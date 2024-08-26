package kubernetes

import (
	"context"
	"fmt"
	"go.uber.org/zap"
	"k8s-maestro/config"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	"k8s.io/client-go/kubernetes"
	"k8s.io/metrics/pkg/client/clientset/versioned"
)

type NodeParameter struct {
}

func (manager *NodeParameter) FindNodes() error {
	clientset := config.Clientset
	metricsClient := config.MetricsClient
	if err, _ := findNodes(clientset, metricsClient); err != nil {
		return nil
	}
	return nil
}

func findNodes(clientset *kubernetes.Clientset, metricsClient *versioned.Clientset) (interface{}, error) {
	// Get list of nodes
	nodes, err := clientset.CoreV1().Nodes().List(context.TODO(), metav1.ListOptions{})
	if err != nil {
		config.Logger.Error("Failed to list nodes", zap.Error(err))
	}

	// Iterate over nodes and get metrics
	for _, node := range nodes.Items {
		nodeMetrics, err := metricsClient.MetricsV1beta1().NodeMetricses().Get(context.TODO(), node.Name, metav1.GetOptions{})
		if err != nil {
			config.Logger.Error("Failed to get metrics for node", zap.String("node", node.Name), zap.Error(err))
			continue
		}

		// Usage
		cpuUsage := nodeMetrics.Usage.Cpu().MilliValue()
		memUsage := nodeMetrics.Usage.Memory().Value()
		gpu := nodeMetrics.Usage["nvidia.com/gpu"]
		gpuUsage := gpu.Value()

		// Total allocatable resources
		cpuAllocatable := node.Status.Allocatable.Cpu().MilliValue()
		memAllocatable := node.Status.Allocatable.Memory().Value()
		gpuAllocatableNode := node.Status.Allocatable["nvidia.com/gpu"]
		gpuAllocatable := gpuAllocatableNode.Value()

		// Total capacity resources
		cpuCapacity := node.Status.Capacity.Cpu().MilliValue()
		memCapacity := node.Status.Capacity.Memory().Value()
		gpuCapacityNode := node.Status.Capacity["nvidia.com/gpu"]
		gpuCapacity := gpuCapacityNode.Value()

		// Usage percentage
		cpuUsagePercent := float64(cpuUsage) / float64(cpuAllocatable) * 100
		memUsagePercent := float64(memUsage) / float64(memAllocatable) * 100
		gpuUsagePercent := float64(gpuUsage) / float64(gpuAllocatable) * 100

		// Allocatable percentage
		cpuAllocatablePercent := float64(cpuAllocatable) / float64(cpuCapacity) * 100
		memAllocatablePercent := float64(memAllocatable) / float64(memCapacity) * 100
		gpuAllocatablePercent := float64(gpuAllocatable) / float64(gpuCapacity) * 100

		fmt.Printf("=========================================\n")
		fmt.Printf("Node: %s\n", node.Name)
		fmt.Printf("CPU Usage: %dm / %dm (%.2f%%)\n", cpuUsage, cpuAllocatable, cpuUsagePercent)
		fmt.Printf("Memory Usage: %d bytes / %d bytes (%.2f%%)\n", memUsage, memAllocatable, memUsagePercent)
		fmt.Printf("GPU Usage: %d / %d (%.2f%%)\n", gpuUsage, gpuAllocatable, gpuUsagePercent)
		fmt.Printf("Total CPU Capacity: %dm\n", cpuCapacity)
		fmt.Printf("Total Memory Capacity: %d bytes\n", memCapacity)
		fmt.Printf("Total GPU Capacity: %d\n", gpuCapacity)
		fmt.Printf("CPU Allocatable: %.2f%% of total capacity\n", cpuAllocatablePercent)
		fmt.Printf("Memory Allocatable: %.2f%% of total capacity\n", memAllocatablePercent)
		fmt.Printf("GPU Allocatable: %.2f%% of total capacity\n", gpuAllocatablePercent)
	}

	return nil, nil
}

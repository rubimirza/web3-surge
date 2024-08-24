```shell
helm repo add csi-driver-s3 https://raw.githubusercontent.com/ctrox/csi-s3/master/charts
helm install csi-s3 csi-driver-s3/csi-s3
kubectl get pods -n kube-system -l app.kubernetes.io/name=aws-mountpoint-s3-csi-driver    

### 创建账号
CLUSTER_NAME=yotta-aws-k8s-cluster
REGION=us-west-2
ROLE_NAME=AmazonEKS_S3_CSI_DriverRole
POLICY_ARN=arn:aws:iam::225989363927:policy/AmazonS3CSIDriverPolicy
eksctl create iamserviceaccount \
    --name s3-csi-driver-sa \
    --namespace kube-system \
    --cluster $CLUSTER_NAME \
    --attach-policy-arn $POLICY_ARN \
    --approve \
    --role-name $ROLE_NAME \
    --region $REGION \
    --role-only
```

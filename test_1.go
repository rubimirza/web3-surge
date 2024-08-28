func Test_Start(t *testing.T) {
	manager := &StartParameter{
		Namespace:   "yotta-saas",
		AppName:     "jupyter-no-gpu",
		Image:       "jupyter/base-notebook",
		Ports:       map[int32]int32{80: 8888},
		Replica:     1,
		Password:    "123456",
		Cpu:         "0.5",
		Memory:      "1Gi",
		Gpu:         "0",
		NeedStorage: true,
	}
	manager.Run()
}


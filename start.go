type StartParameter struct {
	Namespace string
	Image     string
	AppName   string
	Ports     map[int32]int32
	Envs      map[string]string
	Password  string
	Replica   int32
	Cpu       string
	Memory    string
	Gpu       string
}

func Test_int32Ptr(t *testing.T) {
	type args struct {
		i int32
	}
	var tests []struct {
		name string
		args args
		want *int32
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := int32Ptr(tt.args.i); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("int32Ptr() = %v, want %v", got, tt.want)
			}
		})
	}
}

// CheckIngressURLHealth checks if the given ingress URL is healthy.
func CheckIngressURLHealth(url string) bool {
	client := http.Client{
		Timeout: 5 * time.Second,
	}

	resp, err := client.Get(url)
	if err != nil {
		fmt.Printf("Error checking URL: %v\n", err)
		return false
	}
	defer func(Body io.ReadCloser) {
		err := Body.Close()
		if err != nil {
			config.Logger.Error("Error closing response body", zap.Error(err))
		}
	}(resp.Body)

	return resp.StatusCode == http.StatusOK
}

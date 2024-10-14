func (manager *InstanceManager) ConvertInstanceResponse(resource *models.InstanceResource) *models.InstanceResponse {
	return &models.InstanceResponse{
		Id:         resource.ID,
		ExternalId: resource.ExternalId,
		IPAddress:  resource.IPAddress,
		DomainName: resource.DomainName,
		Status:     resource.Status,
		CreatedAt:  resource.CreatedAt,
		UpdatedAt:  resource.UpdatedAt,
	}
}

package api_helpers

import (
	"github.com/gin-gonic/gin"
	"saas-k8s-adapter/constants"
)

type ResponseData struct {
	Code    int         `json:"code"`
	Message string      `json:"message"`
	Data    interface{} `json:"data"`
}

func RespondSimpleJSON(w *gin.Context, code int, message string, payload interface{}) {
	var res ResponseData

	res.Code = code
	res.Message = message
	res.Data = payload

	w.JSON(200, res)
}

func RespondJSON(w *gin.Context, resultCode constants.ResultCode, payload interface{}) {
	var res ResponseData

	res.Code = resultCode.Code
	res.Message = resultCode.Message
	res.Data = payload

	w.JSON(200, res)
}

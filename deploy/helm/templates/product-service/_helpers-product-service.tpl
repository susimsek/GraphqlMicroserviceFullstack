{{- define "product-service.selectorLabels" -}}
app: product-service
release: {{ .Release.Name }}
{{- end }}

{{- define "product-service.labels" -}}
chart: {{ include "app.chart" . }}
{{ include "product-service.selectorLabels" . }}
heritage: {{ .Release.Service }}
app: product-service
{{- end }}
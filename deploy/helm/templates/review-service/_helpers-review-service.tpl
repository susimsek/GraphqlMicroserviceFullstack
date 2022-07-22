{{- define "review-service.selectorLabels" -}}
app: review-service
release: {{ .Release.Name }}
{{- end }}

{{- define "review-service.labels" -}}
chart: {{ include "app.chart" . }}
{{ include "review-service.selectorLabels" . }}
heritage: {{ .Release.Service }}
app: review-service
{{- end }}
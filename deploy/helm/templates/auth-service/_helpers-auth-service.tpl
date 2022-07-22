{{- define "auth-service.selectorLabels" -}}
app: auth-service
release: {{ .Release.Name }}
{{- end }}

{{- define "auth-service.labels" -}}
chart: {{ include "app.chart" . }}
{{ include "auth-service.selectorLabels" . }}
heritage: {{ .Release.Service }}
app: auth-service
{{- end }}
{{- define "apollo-gateway.selectorLabels" -}}
app: apollo-gateway
release: {{ .Release.Name }}
{{- end }}

{{- define "apollo-gateway.labels" -}}
chart: {{ include "app.chart" . }}
{{ include "apollo-gateway.selectorLabels" . }}
heritage: {{ .Release.Service }}
app: apollo-gateway
{{- end }}
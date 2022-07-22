{{- define "web.selectorLabels" -}}
app: web
release: {{ .Release.Name }}
{{- end }}

{{- define "web.labels" -}}
chart: {{ include "app.chart" . }}
{{ include "web.selectorLabels" . }}
heritage: {{ .Release.Service }}
app: web
{{- end }}
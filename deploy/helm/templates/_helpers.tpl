{{/*
Create a default fully qualified name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
*/}}
{{- define "app.fullname" -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "app.shortname" -}}
{{- $name := (.Values.nameOverride | default (printf "%s" .Release.Name)) -}}
{{- printf "%s" $name -}}
{{- end -}}

{{- define "app.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" -}}
{{- end }}

{{/*
Returns the proper service account name depending if an explicit service account name is set
in the values file. If the name is not set it will default to either common.names.fullname if serviceAccount.create
is true or default otherwise.
*/}}
{{- define "app.serviceAccountName" -}}
        {{ default "default" .Values.serviceAccount.name }}
{{- end -}}
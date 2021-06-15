# create cluster
k3d cluster create --config demo.cluster.k3d.yaml

# install argocd
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/v2.0.3/manifests/install.yaml

# wait for pod running Argo CD server ready
kubectl wait -n argocd --for=condition=Ready pod -l app.kubernetes.io/name=argocd-server

# read admin password
# kubectl get secret -n argocd argocd-initial-admin-secret -o jsonpath='{.data.password}' | base64 -d

# connect to the Argo CD API server without exposing the service
# http://localhost:8081
# kubectl port-forward svc/argocd-server -n argocd 8081:443

# /c/dev/programs/argocd-windows-amd64.exe app create auth0-api-webflux-demo \
#   --repo https://github.com/msuperina/auth0-demo.git \
#   --revision feat-dockerhub-k3d-argocd \
#   --path infrastructure/charts/api-webflux-demo \
#   --dest-server https://kubernetes.default.svc \
#   --dest-namespace default \
#   --sync-policy automated

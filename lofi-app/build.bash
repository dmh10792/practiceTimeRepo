

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

docker build "$SCRIPT_DIR" --file "$SCRIPT_DIR/Dockerfile1" --tag need-to-know:1.0.0
docker build "$SCRIPT_DIR" --file "$SCRIPT_DIR/Dockerfile2" --tag need-to-know:2.0.0


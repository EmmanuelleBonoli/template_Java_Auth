source "$(dirname "$0")/../utils.sh"

echo
echo_separator_general
echo_yellow "⚡ Running tests..."
echo_separator_general

if mvn test; then
    echo
    echo_green "✅ All tests passed "
else
    echo_separator
    echo_red "⚠️ Error while running tests. Please fix the errors..."
    echo_separator
    exit 1
fi

source "$(dirname "$0")/../utils.sh"

echo
echo_separator_general
echo_yellow "⚡ Running Checkstyle..."
echo_separator_general
if mvn checkstyle:check; then
    echo_green "✅ Checkstyle passed checks"

    echo
    echo_separator_general
    echo_yellow "⚡ Running Prettier..."
    echo_separator_general
    echo
    if npx prettier --write .; then
        echo
        echo_green "✅ Prettier passed checks"
    else
        echo_separator
        echo_red "⚠️ Error while running Prettier. Please fix the errors."
        echo_separator
        exit 1
    fi
else
    echo_separator
    echo_red "⚠️ Error while running Checkstyle. Please fix the errors."
    echo_separator
    exit 1
fi

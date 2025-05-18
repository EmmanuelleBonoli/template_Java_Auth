source "$(dirname "$0")/../utils.sh"

if [ "$BYPASS_PROTECTED_FILES" = "true" ]; then
    echo_yellow "⚠️ Bypass active: Skipping protected files check."
    exit 0
fi

PROTECTED_FILES=(
    ".husky/pre-commit"
    ".prettierrc.json",
    "checkstyle.xml",
)

PROTECTED_DIR="husky-scripts"

STAGED_FILES=$(git diff --cached --name-only)

BLOCK_COMMIT=false

for FILE in "${PROTECTED_FILES[@]}"; do
    if echo "$STAGED_FILES" | grep -q -E "^$FILE$"; then
        echo_red "❌ Error: The protected file '$FILE' has been modified or added to the commit."
        BLOCK_COMMIT=true
    fi
    if git diff --cached --name-status | grep -q -E "^D\s+$FILE$"; then
        echo_red "❌ Error: The protected file '$FILE' has been deleted."
        BLOCK_COMMIT=true
    fi
done

if echo "$STAGED_FILES" | grep -q -E "^$PROTECTED_DIR/"; then
    echo_red "❌ Error: A file in the protected 'husky-scripts' directory has been modified, added, or deleted."
    BLOCK_COMMIT=true
fi
if git diff --cached --name-status | grep -q -E "^D\s+$PROTECTED_DIR/"; then
    echo_red "❌ Error: A file in the protected 'husky-scripts' directory has been deleted."
    BLOCK_COMMIT=true
fi

if [ "$BLOCK_COMMIT" = true ]; then
    echo_separator
    echo_yellow "👉 Please exclude these files or get prior approval before committing."
    echo_separator
    exit 1
fi

exit 0

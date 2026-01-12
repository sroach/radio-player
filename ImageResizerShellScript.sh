# Source PNGs from drawable folder
SRC_DARK="composeApp/src/commonMain/composeResources/drawable/app_icon.png"
SRC_LIGHT="composeApp/src/commonMain/composeResources/drawable/app_icon_light.png"
SRC_TINTED="composeApp/src/commonMain/composeResources/drawable/app_icon_tinted.png"

# Destination paths
APPICON_DEST="iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"
LOCK_DEST="iosApp/iosApp/Assets.xcassets/lockscreen.imageset"
FAV_DEST="iosApp/iosApp/Assets.xcassets/favicon.imageset"

# Clean and ensure directories exist
rm -rf "$APPICON_DEST"/*
mkdir -p "$APPICON_DEST"
mkdir -p "$LOCK_DEST"
mkdir -p "$FAV_DEST"

# 1. Copy App Icons (Single 1024x1024 assets)
cp "$SRC_LIGHT" "$APPICON_DEST/icon_light_1024.png"
cp "$SRC_DARK" "$APPICON_DEST/icon_dark_1024.png"
cp "$SRC_TINTED" "$APPICON_DEST/icon_tinted_1024.png"

# 2. Lockscreen Imageset
cp "$SRC_LIGHT" "$LOCK_DEST/lockscreen.png"

# 3. Favicon Resizing (Required sizes for web/pwa)
magick "$SRC_LIGHT" -resize 64x64 "$FAV_DEST/favicon.png"
magick "$SRC_LIGHT" -resize 128x128 "$FAV_DEST/favicon@2x.png"
magick "$SRC_LIGHT" -resize 192x192 "$FAV_DEST/favicon@3x.png"

# --- GENERATE LOCKSCREEN ---
mkdir -p "$LOCK_DEST"
cp "$SRC_LIGHT" "$LOCK_DEST/lockscreen.png"
cat <<EOF > "$LOCK_DEST/Contents.json"
{
  "images": [
    {"filename": "lockscreen.png", "idiom": "universal", "scale": "1x"},
    {"idiom": "universal", "scale": "2x"},
    {"idiom": "universal", "scale": "3x"}
  ],
  "info": {"author": "xcode", "version": 1}
}
EOF

# --- GENERATE FAVICON ---
mkdir -p "$FAV_DEST"
magick "$SRC_LIGHT" -resize 64x64 "$FAV_DEST/favicon.png"
magick "$SRC_LIGHT" -resize 128x128 "$FAV_DEST/favicon@2x.png"
magick "$SRC_LIGHT" -resize 192x192 "$FAV_DEST/favicon@3x.png"
cat <<EOF > "$FAV_DEST/Contents.json"
{
  "images": [
    {"filename": "favicon.png", "idiom": "universal", "scale": "1x"},
    {"filename": "favicon@2x.png", "idiom": "universal", "scale": "2x"},
    {"filename": "favicon@3x.png", "idiom": "universal", "scale": "3x"}
  ],
  "info": {"author": "xcode", "version": 1}
}
EOF
echo "Master icons copied. Please ensure Xcode is set to 'Single Size' for AppIcon."
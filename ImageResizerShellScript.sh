SVG="composeApp/src/commonMain/composeResources/drawable/app_icon.svg"
DEST="iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"

for size in 16 20 29 32 40 48 50 55 57 58 60 64 66 72 76 80 87 88 92 100 102 108 114 120 128 144 152 167 172 180 196 216 234 256 258 512 1024; do
  magick -background none "$SVG" -resize ${size}x${size} "$DEST/${size}.png"
done

DESTLOCK="iosApp/iosApp/Assets.xcassets/lockscreen.imageset"

for f in "1024" "1024 1" "1024 2" "1024 3" "1024 4" "1024 5" "1024 6" "1024 7" "1024 8"; do
  magick -background none "$SVG" -resize 1024x1024 "$DESTLOCK/$f.png"
done

DESTFAV="iosApp/iosApp/Assets.xcassets/favicon.imageset"

magick -background none "$SVG" -resize 64x64 "$DESTFAV/favicon.png"
magick -background none "$SVG" -resize 128x128 "$DESTFAV/favicon@2x.png"
magick -background none "$SVG" -resize 192x192 "$DESTFAV/favicon@3x.png"
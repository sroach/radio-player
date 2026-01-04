SVG="composeApp/src/commonMain/composeResources/drawable/app_icon.svg"

magick -background none "$SVG" -resize 108x108 composeApp/src/androidMain/res/mipmap-mdpi/ic_launcher_foreground.png
magick -background none "$SVG" -resize 162x162 composeApp/src/androidMain/res/mipmap-hdpi/ic_launcher_foreground.png
magick -background none "$SVG" -resize 216x216 composeApp/src/androidMain/res/mipmap-xhdpi/ic_launcher_foreground.png
magick -background none "$SVG" -resize 324x324 composeApp/src/androidMain/res/mipmap-xxhdpi/ic_launcher_foreground.png
magick -background none "$SVG" -resize 432x432 composeApp/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_foreground.png

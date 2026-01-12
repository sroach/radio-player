import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}


struct LaunchScreenView: View {
    var body: some View {
        ZStack {
            // Rule #7: Atmospheric Background (Matching goldenArrowheadDark)
            LinearGradient(
                colors: [
                    Color(red: 0.039, green: 0.059, blue: 0.047), // Deep black-green
                    Color(red: 0.078, green: 0.102, blue: 0.039)  // Gold-tinged black
                ],
                startPoint: .top,
                endPoint: .bottom
            )
                .ignoresSafeArea()

            VStack(spacing: 32) {
                // Simplified Equalizer Visual (Geometric Depth)
                HStack(alignment: .bottom, spacing: 8) {
                    EqualizerBar(color: Color(red: 0.808, green: 0.067, blue: 0.149), height: 60) // FlagRed
                    EqualizerBar(color: Color(red: 1.0, green: 0.804, blue: 0.0), height: 100)    // FlagGold
                    EqualizerBar(color: Color(red: 0.0, green: 0.592, blue: 0.224), height: 80)     // FlagGreen
                    EqualizerBar(color: Color(red: 1.0, green: 0.804, blue: 0.0), height: 90)     // FlagGold
                    EqualizerBar(color: Color(red: 0.808, green: 0.067, blue: 0.149), height: 50)  // FlagRed
                }
                .frame(height: 120)

                VStack(spacing: 12) {
                    // Rule #2: Letter Spacing on Headlines
                    Text("GY TUNES")
                        .font(.system(size: 32, weight: .black, design: .monospaced))
                        .kerning(4) // 4.sp letter spacing
                        .foregroundColor(Color(red: 1.0, green: 0.804, blue: 0.0)) // FlagGold

                    Text("Your Sound of Home")
                        .font(.system(size: 16, weight: .light, design: .rounded))
                        .italic()
                        .foregroundColor(.white.opacity(0.7))
                }
            }
        }
    }
}

struct EqualizerBar: View {
    let color: Color
    let height: CGFloat

    var body: some View {
        RoundedRectangle(cornerRadius: 4)
            .fill(color)
            .frame(width: 16, height: height)
            .opacity(0.9)
            .shadow(color: color.opacity(0.5), radius: 8) // Atmosphere glow
    }
}
#Preview {
    LaunchScreenView()
}

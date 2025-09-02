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
            // Background gradient matching your app icon
            LinearGradient(
                colors: [
                    Color(red: 1.0, green: 0.843, blue: 0.0), // Gold
                    Color(red: 1.0, green: 0.42, blue: 0.21), // Orange/Red
                    Color(red: 0.133, green: 0.545, blue: 0.133), // Green
                    Color(red: 0.118, green: 0.565, blue: 1.0) // Blue
                ],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
                .ignoresSafeArea()

            VStack(spacing: 20) {
                // App Icon or Logo
                Image("AppIcon") // You'll need to add this to Assets
                    .resizable()
                    .frame(width: 120, height: 120)
                    .clipShape(RoundedRectangle(cornerRadius: 26))
                    .shadow(radius: 10)

                // App Name
                Text("Radio Guyana")
                    .font(.system(size: 32, weight: .bold, design: .rounded))
                    .foregroundColor(.white)
                    .shadow(color: .black.opacity(0.3), radius: 2, x: 0, y: 2)

                // Tagline
                Text("Your Sound of Home")
                    .font(.system(size: 16, weight: .medium, design: .rounded))
                    .foregroundColor(.white.opacity(0.9))
                    .shadow(color: .black.opacity(0.3), radius: 1, x: 0, y: 1)
            }
        }
    }
}

#Preview {
    LaunchScreenView()
}

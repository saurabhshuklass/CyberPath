package com.example.cyberpath.repository

import com.example.cyberpath.model.Question

object BeginnerQuestions {

    fun getQuestions(): List<Question> {

        return listOf(

            Question(
                1,
                "What does CIA stand for in Cyber Security?",
                listOf(
                    "Central Internet Agency",
                    "Confidentiality, Integrity, Availability",
                    "Cyber Intelligence Authority",
                    "Control, Identification, Access"
                ),
                1
            ),

            Question(
                2,
                "Which of the following is the strongest password?",
                listOf(
                    "password123",
                    "12345678",
                    "Cyber@2026#Secure",
                    "qwerty"
                ),
                2
            ),

            Question(
                3,
                "Which attack tricks users into revealing confidential information?",
                listOf(
                    "Phishing",
                    "Firewall",
                    "VPN",
                    "Encryption"
                ),
                0
            ),

            Question(
                4,
                "HTTPS mainly provides:",
                listOf(
                    "Faster Internet",
                    "Secure Communication",
                    "Free Wi-Fi",
                    "Cloud Storage"
                ),
                1
            ),

            Question(
                5,
                "Which software protects your computer from viruses?",
                listOf(
                    "Notepad",
                    "Calculator",
                    "Antivirus",
                    "Paint"
                ),
                2
            ),

            Question(
                6,
                "Before clicking a link in an email you should:",
                listOf(
                    "Click immediately",
                    "Verify the sender and URL",
                    "Forward it",
                    "Ignore warnings"
                ),
                1
            ),

            Question(
                7,
                "Malware means:",
                listOf(
                    "Malicious Software",
                    "Microsoft Software",
                    "Memory Software",
                    "Mobile Hardware"
                ),
                0
            ),

            Question(
                8,
                "Which is a safe browsing habit?",
                listOf(
                    "Downloading cracked software",
                    "Ignoring browser warnings",
                    "Keeping software updated",
                    "Sharing passwords"
                ),
                2
            ),

            Question(
                9,
                "Which device filters network traffic?",
                listOf(
                    "Firewall",
                    "Keyboard",
                    "Scanner",
                    "Monitor"
                ),
                0
            ),

            Question(
                10,
                "Which authentication method is the most secure?",
                listOf(
                    "Password only",
                    "PIN only",
                    "Two-Factor Authentication",
                    "Username only"
                ),
                2
            ),

            Question(
                11,
                "What is the primary purpose of a firewall?",
                listOf(
                    "Play videos",
                    "Filter network traffic",
                    "Store passwords",
                    "Increase internet speed"
                ),
                1
            ),

            Question(
                12,
                "Which of these is an example of malware?",
                listOf(
                    "Trojan Horse",
                    "Google Chrome",
                    "Microsoft Word",
                    "Windows Explorer"
                ),
                0
            ),

            Question(
                13,
                "What should you do if you receive a suspicious email?",
                listOf(
                    "Open all attachments",
                    "Reply immediately",
                    "Delete or report it",
                    "Forward it to everyone"
                ),
                2
            ),

            Question(
                14,
                "Which symbol is commonly found in a strong password?",
                listOf(
                    "@",
                    "Only letters",
                    "Only numbers",
                    "Only spaces"
                ),
                0
            ),

            Question(
                15,
                "What does VPN stand for?",
                listOf(
                    "Virtual Private Network",
                    "Verified Public Network",
                    "Virtual Public Node",
                    "Very Private Node"
                ),
                0
            ),

            Question(
                16,
                "Which one is a phishing indicator?",
                listOf(
                    "Official company domain",
                    "Poor grammar and urgent language",
                    "HTTPS website",
                    "Known contact"
                ),
                1
            ),

            Question(
                17,
                "Which of the following is NOT a cyber attack?",
                listOf(
                    "Phishing",
                    "Ransomware",
                    "Firewall",
                    "Spyware"
                ),
                2
            ),

            Question(
                18,
                "Why should software be updated regularly?",
                listOf(
                    "To fix security vulnerabilities",
                    "To make passwords weaker",
                    "To slow the computer",
                    "To remove antivirus"
                ),
                0
            ),

            Question(
                19,
                "Which of these is a secure website?",
                listOf(
                    "http://bank-login.xyz",
                    "https://www.bank.com",
                    "http://secure-login.net",
                    "http://bank-free.com"
                ),
                1
            ),

            Question(
                20,
                "Which practice helps protect your online accounts?",
                listOf(
                    "Using the same password everywhere",
                    "Enabling Two-Factor Authentication",
                    "Sharing passwords with friends",
                    "Writing passwords on paper"
                ),
                1
            ),

            Question(
                21,
                "What is Social Engineering?",
                listOf(
                    "Building social media apps",
                    "Manipulating people to reveal confidential information",
                    "Creating computer networks",
                    "Managing online communities"
                ),
                1
            ),

            Question(
                22,
                "Which of the following is the safest way to store passwords?",
                listOf(
                    "Write them on paper",
                    "Save them in a text file",
                    "Use a Password Manager",
                    "Memorize only one password for all accounts"
                ),
                2
            ),

            Question(
                23,
                "Why is public Wi-Fi considered risky?",
                listOf(
                    "It is always slow",
                    "Attackers may intercept your data",
                    "It consumes more battery",
                    "It damages your device"
                ),
                1
            ),

            Question(
                24,
                "Which action is safest when using public Wi-Fi?",
                listOf(
                    "Access online banking",
                    "Disable your antivirus",
                    "Use a VPN",
                    "Share sensitive files"
                ),
                2
            ),

            Question(
                25,
                "What is the purpose of backing up your data?",
                listOf(
                    "Increase internet speed",
                    "Recover data after loss or ransomware",
                    "Improve graphics",
                    "Reduce RAM usage"
                ),
                1
            ),

            Question(
                26,
                "Which is an example of Multi-Factor Authentication (MFA)?",
                listOf(
                    "Password only",
                    "Username only",
                    "Password + OTP",
                    "Email address only"
                ),
                2
            ),

            Question(
                27,
                "What should you check before downloading software?",
                listOf(
                    "The weather",
                    "Official website and trusted source",
                    "Battery percentage",
                    "Screen brightness"
                ),
                1
            ),

            Question(
                28,
                "What is the safest way to handle unknown USB drives?",
                listOf(
                    "Plug them in immediately",
                    "Scan them with antivirus before use",
                    "Format your computer",
                    "Share them with friends"
                ),
                1
            ),

            Question(
                29,
                "Which browser feature helps identify secure websites?",
                listOf(
                    "Bookmark icon",
                    "Padlock icon",
                    "Refresh button",
                    "Download button"
                ),
                1
            ),

            Question(
                30,
                "Cyber Hygiene refers to:",
                listOf(
                    "Cleaning computer hardware",
                    "Regular security practices to keep devices safe",
                    "Deleting all files every week",
                    "Using only mobile devices"
                ),
                1
            ),

            Question(
                31,
                "What is ransomware?",
                listOf(
                    "Software that speeds up a computer",
                    "Malware that encrypts files and demands payment",
                    "A type of antivirus",
                    "A web browser"
                ),
                1
            ),

            Question(
                32,
                "Spyware is designed to:",
                listOf(
                    "Improve system performance",
                    "Monitor user activities without permission",
                    "Protect against viruses",
                    "Encrypt files"
                ),
                1
            ),

            Question(
                33,
                "A brute force attack attempts to:",
                listOf(
                    "Guess passwords by trying many combinations",
                    "Physically damage a computer",
                    "Delete antivirus software",
                    "Increase internet speed"
                ),
                0
            ),

            Question(
                34,
                "Which authentication method uses fingerprints or facial recognition?",
                listOf(
                    "Password Authentication",
                    "Biometric Authentication",
                    "PIN Authentication",
                    "OTP Authentication"
                ),
                1
            ),

            Question(
                35,
                "Which of the following is an example of cloud storage?",
                listOf(
                    "Google Drive",
                    "Calculator",
                    "Notepad",
                    "Task Manager"
                ),
                0
            ),

            Question(
                36,
                "Why should you log out from shared computers?",
                listOf(
                    "To save electricity",
                    "To prevent unauthorized access",
                    "To increase internet speed",
                    "To clean browser history"
                ),
                1
            ),

            Question(
                37,
                "Browser cookies are mainly used to:",
                listOf(
                    "Store website preferences and login sessions",
                    "Increase RAM",
                    "Remove viruses",
                    "Encrypt files"
                ),
                0
            ),

            Question(
                38,
                "Encryption is used to:",
                listOf(
                    "Hide information so only authorized users can read it",
                    "Delete files permanently",
                    "Speed up downloads",
                    "Compress images"
                ),
                0
            ),

            Question(
                39,
                "What is the safest way to share confidential files?",
                listOf(
                    "Upload them publicly",
                    "Send through encrypted and trusted services",
                    "Share on social media",
                    "Use public Wi-Fi without protection"
                ),
                1
            ),

            Question(
                40,
                "Which of the following improves mobile phone security?",
                listOf(
                    "Installing apps from unknown sources",
                    "Keeping the operating system updated",
                    "Disabling screen lock",
                    "Sharing OTPs with others"
                ),
                1
            ),

            Question(
                41,
                "What should you do if you receive an unexpected OTP on your phone?",
                listOf(
                    "Share it with the caller",
                    "Ignore it and never share the OTP",
                    "Post it on social media",
                    "Forward it to friends"
                ),
                1
            ),

            Question(
                42,
                "Which of the following is an example of identity theft?",
                listOf(
                    "Using someone else's personal information without permission",
                    "Changing your profile picture",
                    "Creating a backup of your files",
                    "Updating your password"
                ),
                0
            ),

            Question(
                43,
                "What is the safest way to download software?",
                listOf(
                    "From random websites",
                    "From the official developer's website",
                    "Through unknown email links",
                    "Using pirated software websites"
                ),
                1
            ),

            Question(
                44,
                "A QR code scam usually tricks users into:",
                listOf(
                    "Scanning a malicious QR code that opens fake websites",
                    "Improving internet speed",
                    "Updating their operating system",
                    "Increasing battery life"
                ),
                0
            ),

            Question(
                45,
                "Why should you avoid using the same password for multiple accounts?",
                listOf(
                    "It is difficult to remember",
                    "One compromised account can expose all other accounts",
                    "It makes websites slower",
                    "It reduces internet speed"
                ),
                1
            ),

            Question(
                46,
                "What is meant by your digital footprint?",
                listOf(
                    "The physical size of your computer",
                    "The information you leave behind while using the internet",
                    "The amount of RAM in your device",
                    "Your internet speed history"
                ),
                1
            ),

            Question(
                47,
                "Which practice helps protect your personal data online?",
                listOf(
                    "Sharing personal details publicly",
                    "Reviewing privacy settings regularly",
                    "Using weak passwords",
                    "Ignoring software updates"
                ),
                1
            ),

            Question(
                48,
                "Which of the following is considered ethical online behavior?",
                listOf(
                    "Respecting others' privacy and following cyber laws",
                    "Downloading pirated software",
                    "Sharing copyrighted files illegally",
                    "Creating fake social media accounts"
                ),
                0
            ),

            Question(
                49,
                "What should you verify before making an online payment?",
                listOf(
                    "The website uses HTTPS and is trustworthy",
                    "The website has colorful graphics",
                    "The website loads quickly",
                    "The website has many advertisements"
                ),
                0
            ),

            Question(
                50,
                "Which of the following is the BEST cybersecurity habit?",
                listOf(
                    "Regularly updating software, using strong passwords, and enabling Two-Factor Authentication",
                    "Using the same password everywhere",
                    "Ignoring security notifications",
                    "Clicking every email attachment"
                ),
                0
            )

        )

    }

}
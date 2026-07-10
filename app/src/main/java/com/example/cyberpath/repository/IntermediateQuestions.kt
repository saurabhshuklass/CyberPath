package com.example.cyberpath.repository

import com.example.cyberpath.model.Question

object IntermediateQuestions {

    fun getQuestions(): List<Question> {

        return listOf(

            Question(
                1,
                "Which protocol is commonly used for secure web browsing?",
                listOf(
                    "HTTP",
                    "FTP",
                    "HTTPS",
                    "SMTP"
                ),
                2
            ),

            Question(
                2,
                "Which device monitors and filters incoming and outgoing network traffic?",
                listOf(
                    "Router",
                    "Switch",
                    "Hub",
                    "Firewall"
                ),
                3
            ),

            Question(
                3,
                "Which attack attempts to overwhelm a server with massive traffic?",
                listOf(
                    "SQL Injection",
                    "DDoS Attack",
                    "Phishing",
                    "Brute Force"
                ),
                1
            ),

            Question(
                4,
                "Which tool encrypts internet traffic on public Wi-Fi?",
                listOf(
                    "VPN",
                    "Firewall",
                    "IDS",
                    "DNS"
                ),
                0
            ),

            Question(
                5,
                "Which malware secretly records a user's activities?",
                listOf(
                    "Worm",
                    "Trojan",
                    "Spyware",
                    "Adware"
                ),
                2
            ),

            Question(
                6,
                "What does IDS stand for?",
                listOf(
                    "Internet Detection Service",
                    "Intrusion Detection System",
                    "Internal Data Security",
                    "Integrated Defense Software"
                ),
                1
            ),

            Question(
                7,
                "Which password is the strongest?",
                listOf(
                    "Admin123",
                    "Password123",
                    "Welcome2025",
                    "K!m@9xP#72L"
                ),
                3
            ),

            Question(
                8,
                "Which attack exploits weaknesses in database queries?",
                listOf(
                    "SQL Injection",
                    "Phishing",
                    "Spoofing",
                    "DoS"
                ),
                0
            ),

            Question(
                9,
                "What is the primary purpose of antivirus software?",
                listOf(
                    "Increase internet speed",
                    "Detect and remove malware",
                    "Store passwords",
                    "Block websites"
                ),
                1
            ),

            Question(
                10,
                "Which Wi-Fi encryption method is currently the most secure?",
                listOf(
                    "WEP",
                    "WPA",
                    "WPA2",
                    "WPA3"
                ),
                3
            ),

            Question(
                11,
                "Which of the following is an example of Multi-Factor Authentication (MFA)?",
                listOf(
                    "Username and Password",
                    "Password and OTP",
                    "Email Address only",
                    "PIN only"
                ),
                1
            ),

            Question(
                12,
                "A company's employees receive emails asking them to verify login credentials on a fake website. This is an example of:",
                listOf(
                    "Phishing",
                    "Spoofing",
                    "SQL Injection",
                    "Brute Force Attack"
                ),
                0
            ),

            Question(
                13,
                "Which protocol is commonly used for secure remote login to Linux servers?",
                listOf(
                    "FTP",
                    "HTTP",
                    "SSH",
                    "Telnet"
                ),
                2
            ),

            Question(
                14,
                "What is the main purpose of a VPN?",
                listOf(
                    "Increase RAM",
                    "Improve CPU performance",
                    "Encrypt internet traffic",
                    "Remove malware"
                ),
                2
            ),

            Question(
                15,
                "Which type of malware can spread without user interaction?",
                listOf(
                    "Trojan",
                    "Worm",
                    "Spyware",
                    "Adware"
                ),
                1
            ),

            Question(
                16,
                "Which attack attempts many password combinations until the correct one is found?",
                listOf(
                    "Phishing",
                    "Ransomware",
                    "Brute Force Attack",
                    "MITM Attack"
                ),
                2
            ),

            Question(
                17,
                "Which of the following is the BEST practice for protecting sensitive company data?",
                listOf(
                    "Share passwords with teammates",
                    "Store passwords in Notepad",
                    "Enable encryption and access control",
                    "Disable antivirus"
                ),
                2
            ),

            Question(
                18,
                "An attacker secretly intercepts communication between two users. This attack is called:",
                listOf(
                    "SQL Injection",
                    "Cross-Site Scripting",
                    "Denial of Service",
                    "Man-in-the-Middle (MITM)"
                ),
                3
            ),

            Question(
                19,
                "Which of the following should you verify before clicking a shortened URL?",
                listOf(
                    "Its destination using a URL expander or preview",
                    "The website's logo",
                    "The browser theme",
                    "The screen brightness"
                ),
                0
            ),

            Question(
                20,
                "Which cybersecurity principle ensures that information is accessible whenever authorized users need it?",
                listOf(
                    "Confidentiality",
                    "Availability",
                    "Integrity",
                    "Authentication"
                ),
                1
            ),

            Question(
                21,
                "What is the primary purpose of DNS (Domain Name System)?",
                listOf(
                    "Encrypt internet traffic",
                    "Translate domain names into IP addresses",
                    "Block malicious websites",
                    "Store passwords"
                ),
                1
            ),

            Question(
                22,
                "Which protocol automatically assigns IP addresses to devices on a network?",
                listOf(
                    "DNS",
                    "FTP",
                    "DHCP",
                    "SMTP"
                ),
                2
            ),

            Question(
                23,
                "A website displays a warning that its security certificate is invalid. What should you do?",
                listOf(
                    "Ignore the warning and continue",
                    "Refresh the page repeatedly",
                    "Close the website and verify its authenticity",
                    "Disable browser security"
                ),
                2
            ),

            Question(
                24,
                "What is the main purpose of creating regular backups?",
                listOf(
                    "Recover data after hardware failure or ransomware",
                    "Increase internet speed",
                    "Reduce CPU usage",
                    "Improve Wi-Fi signal"
                ),
                0
            ),

            Question(
                25,
                "Business Email Compromise (BEC) attacks mainly target:",
                listOf(
                    "Gaming accounts",
                    "Company employees through fake business emails",
                    "Wi-Fi routers",
                    "Operating systems"
                ),
                1
            ),

            Question(
                26,
                "Why should you review app permissions before installing a mobile application?",
                listOf(
                    "To improve battery life",
                    "To reduce internet usage",
                    "To prevent unnecessary access to personal data",
                    "To speed up app installation"
                ),
                2
            ),

            Question(
                27,
                "Which cloud security practice helps protect stored data?",
                listOf(
                    "Disabling encryption",
                    "Sharing accounts with everyone",
                    "Using weak passwords",
                    "Enabling encryption and access controls"
                ),
                3
            ),

            Question(
                28,
                "The Zero Trust security model assumes:",
                listOf(
                    "Everyone inside the network is trusted",
                    "No user or device is trusted by default",
                    "Only external users are risky",
                    "Antivirus alone is enough"
                ),
                1
            ),

            Question(
                29,
                "What is password hashing mainly used for?",
                listOf(
                    "Securely storing passwords",
                    "Increasing internet speed",
                    "Compressing files",
                    "Scanning malware"
                ),
                0
            ),

            Question(
                30,
                "Which of the following is considered an insider threat?",
                listOf(
                    "A hacker from another country",
                    "A virus downloaded from the internet",
                    "An employee intentionally leaking confidential data",
                    "A firewall blocking traffic"
                ),
                2
            ),

            Question(
                31,
                "What is the primary goal of a SQL Injection attack?",
                listOf(
                    "Steal or manipulate database information",
                    "Slow down the internet connection",
                    "Encrypt user files",
                    "Block network traffic"
                ),
                0
            ),

            Question(
                32,
                "Cross-Site Scripting (XSS) mainly targets:",
                listOf(
                    "Operating Systems",
                    "Web Applications",
                    "Network Switches",
                    "Firewalls"
                ),
                1
            ),

            Question(
                33,
                "Which attack steals a user's active login session?",
                listOf(
                    "Phishing",
                    "Brute Force",
                    "Session Hijacking",
                    "DDoS"
                ),
                2
            ),

            Question(
                34,
                "Digital certificates are primarily used to:",
                listOf(
                    "Increase internet speed",
                    "Verify the identity of websites and enable secure communication",
                    "Store passwords",
                    "Remove malware"
                ),
                1
            ),

            Question(
                35,
                "What is the purpose of a security audit?",
                listOf(
                    "Find and evaluate security weaknesses",
                    "Improve graphics performance",
                    "Increase RAM",
                    "Install software updates"
                ),
                0
            ),

            Question(
                36,
                "A Vulnerability Assessment mainly focuses on:",
                listOf(
                    "Repairing hardware",
                    "Finding security weaknesses",
                    "Developing software",
                    "Increasing storage capacity"
                ),
                1
            ),

            Question(
                37,
                "Penetration Testing is performed to:",
                listOf(
                    "Physically repair computers",
                    "Test internet speed",
                    "Simulate attacks to identify vulnerabilities",
                    "Install antivirus software"
                ),
                2
            ),

            Question(
                38,
                "What does SIEM stand for?",
                listOf(
                    "Secure Internet Email Management",
                    "System Information Event Monitoring",
                    "Security Information and Event Management",
                    "Software Integration Error Manager"
                ),
                2
            ),

            Question(
                39,
                "Which phase comes FIRST in an Incident Response process?",
                listOf(
                    "Recovery",
                    "Containment",
                    "Preparation",
                    "Eradication"
                ),
                2
            ),

            Question(
                40,
                "The Principle of Least Privilege means:",
                listOf(
                    "Every user should have administrator access",
                    "Users should receive only the permissions necessary to perform their tasks",
                    "Passwords should never expire",
                    "Everyone should use the same account"
                ),
                1
            ),

            Question(
                41,
                "What is Digital Forensics primarily used for?",
                listOf(
                    "Recovering deleted games",
                    "Investigating cyber incidents by collecting digital evidence",
                    "Improving internet speed",
                    "Updating operating systems"
                ),
                1
            ),

            Question(
                42,
                "What is the purpose of a Risk Assessment?",
                listOf(
                    "Identify and evaluate security risks",
                    "Increase computer performance",
                    "Install software updates",
                    "Monitor internet speed"
                ),
                0
            ),

            Question(
                43,
                "Patch Management helps organizations by:",
                listOf(
                    "Deleting old files",
                    "Backing up databases",
                    "Fixing known security vulnerabilities",
                    "Creating user accounts"
                ),
                2
            ),

            Question(
                44,
                "Which is the BEST example of Security Awareness Training?",
                listOf(
                    "Teaching employees how to identify phishing emails",
                    "Replacing computer hardware",
                    "Increasing RAM",
                    "Formatting hard drives"
                ),
                0
            ),

            Question(
                45,
                "An employee intentionally steals confidential company files. This is an example of:",
                listOf(
                    "External attack",
                    "Insider Threat",
                    "Firewall failure",
                    "System update"
                ),
                1
            ),

            Question(
                46,
                "What is a Password Spraying attack?",
                listOf(
                    "Trying many passwords on one account",
                    "Using one common password against many accounts",
                    "Sharing passwords through email",
                    "Encrypting passwords"
                ),
                1
            ),

            Question(
                47,
                "A Zero-Day Vulnerability is:",
                listOf(
                    "A vulnerability already patched",
                    "A hardware failure",
                    "A newly discovered vulnerability with no available fix",
                    "A firewall configuration issue"
                ),
                2
            ),

            Question(
                48,
                "What is the purpose of Data Loss Prevention (DLP)?",
                listOf(
                    "Increase storage capacity",
                    "Monitor employee attendance",
                    "Prevent unauthorized sharing of sensitive information",
                    "Improve network speed"
                ),
                2
            ),

            Question(
                49,
                "A Security Policy mainly provides:",
                listOf(
                    "Rules and guidelines for protecting organizational information",
                    "Gaming instructions",
                    "Computer hardware specifications",
                    "Software license keys"
                ),
                0
            ),

            Question(
                50,
                "Defense in Depth refers to:",
                listOf(
                    "Using only antivirus software",
                    "Protecting systems with multiple layers of security controls",
                    "Blocking every website",
                    "Changing passwords every hour"
                ),
                1
            )

        )

    }

}
package com.example.cyberpath.repository

import com.example.cyberpath.model.Question

object AdvancedQuestions {

    fun getQuestions(): List<Question> {

        return listOf(

            Question(
                1,
                "Which encryption algorithm is symmetric?",
                listOf(
                    "RSA",
                    "ECC",
                    "AES",
                    "DSA"
                ),
                2
            ),

            Question(
                2,
                "RSA is mainly used for:",
                listOf(
                    "Symmetric encryption",
                    "Public-key encryption",
                    "File compression",
                    "Network routing"
                ),
                1
            ),

            Question(
                3,
                "Which hashing algorithm is considered more secure today?",
                listOf(
                    "MD5",
                    "SHA-256",
                    "SHA-1",
                    "CRC32"
                ),
                1
            ),

            Question(
                4,
                "Which OWASP Top 10 vulnerability involves inserting malicious SQL queries?",
                listOf(
                    "Cross-Site Scripting",
                    "Broken Authentication",
                    "SQL Injection",
                    "Security Misconfiguration"
                ),
                2
            ),

            Question(
                5,
                "What is the main purpose of a SIEM solution?",
                listOf(
                    "Manage databases",
                    "Monitor and analyze security events",
                    "Increase internet speed",
                    "Encrypt files"
                ),
                1
            ),

            Question(
                6,
                "Which penetration testing phase involves gathering information about the target?",
                listOf(
                    "Reconnaissance",
                    "Exploitation",
                    "Reporting",
                    "Post-Exploitation"
                ),
                0
            ),

            Question(
                7,
                "Which attack exploits software before a security patch is available?",
                listOf(
                    "Brute Force",
                    "Phishing",
                    "Zero-Day Attack",
                    "Password Spraying"
                ),
                2
            ),

            Question(
                8,
                "The MITRE ATT&CK Framework is primarily used to:",
                listOf(
                    "Design websites",
                    "Classify attacker tactics and techniques",
                    "Encrypt emails",
                    "Manage cloud storage"
                ),
                1
            ),

            Question(
                9,
                "Which principle requires verifying every user and device before granting access?",
                listOf(
                    "Zero Trust",
                    "Open Access",
                    "Guest Authentication",
                    "Single Sign-On"
                ),
                0
            ),

            Question(
                10,
                "Buffer Overflow attacks mainly target:",
                listOf(
                    "Network cables",
                    "Memory management vulnerabilities",
                    "Browser cookies",
                    "Wireless signals"
                ),
                1
            ),

                    Question(
                    11,
            "A company wants to securely exchange encryption keys over the internet. Which algorithm is most suitable?",
            listOf(
                "AES",
                "DES",
                "RSA",
                "MD5"
            ),
            2
        ),

        Question(
            12,
            "An attacker steals a hashed password database. Which practice makes cracking passwords more difficult?",
            listOf(
                "Using Salt with Hashing",
                "Using HTTP",
                "Storing passwords in plain text",
                "Reducing password length"
            ),
            0
        ),

        Question(
            13,
            "A security analyst notices repeated failed login attempts from multiple IP addresses targeting one account. This is most likely:",
            listOf(
                "Phishing",
                "Password Spraying",
                "SQL Injection",
                "DNS Spoofing"
            ),
            1
        ),

        Question(
            14,
            "Which OWASP Top 10 vulnerability occurs when user input is displayed without proper validation?",
            listOf(
                "Broken Access Control",
                "Cross-Site Scripting (XSS)",
                "Security Misconfiguration",
                "Server-Side Request Forgery"
            ),
            1
        ),

        Question(
            15,
            "During a penetration test, which phase comes immediately after Reconnaissance?",
            listOf(
                "Reporting",
                "Exploitation",
                "Scanning and Enumeration",
                "Cleanup"
            ),
            2
        ),

        Question(
            16,
            "A company wants to detect suspicious activities from different security devices in one dashboard. Which solution should they use?",
            listOf(
                "Firewall",
                "SIEM",
                "Switch",
                "Load Balancer"
            ),
            1
        ),

        Question(
            17,
            "Which type of attack tricks a DNS server into providing a fake IP address?",
            listOf(
                "DNS Spoofing",
                "Brute Force",
                "Buffer Overflow",
                "ARP Scanning"
            ),
            0
        ),

        Question(
            18,
            "A developer wants to prevent SQL Injection attacks. Which approach is BEST?",
            listOf(
                "Disable HTTPS",
                "Use longer passwords",
                "Use Parameterized Queries",
                "Store SQL queries in text files"
            ),
            2
        ),

        Question(
            19,
            "Which cybersecurity framework helps organizations identify, protect, detect, respond, and recover from cyber threats?",
            listOf(
                "ISO 9001",
                "Agile",
                "NIST Cybersecurity Framework",
                "ITIL"
            ),
            2
        ),

        Question(
            20,
            "An attacker intercepts communication between two parties without their knowledge. Which attack is this?",
            listOf(
                "Ransomware",
                "Cross-Site Scripting",
                "Brute Force",
                "Man-in-the-Middle (MITM)"
            ),
            3
        ),

                Question(
                21,
            "What is the primary purpose of a Digital Signature?",
            listOf(
                "Compress files",
                "Verify authenticity and integrity of data",
                "Increase internet speed",
                "Encrypt Wi-Fi passwords"
            ),
            1
        ),

        Question(
            22,
            "Public Key Infrastructure (PKI) is mainly used to:",
            listOf(
                "Manage digital certificates and public keys",
                "Increase RAM",
                "Store passwords",
                "Monitor network traffic"
            ),
            0
        ),

        Question(
            23,
            "A company integrates security testing during every phase of software development. This follows:",
            listOf(
                "Waterfall Model",
                "DevOps",
                "Secure SDLC",
                "Agile Testing"
            ),
            2
        ),

        Question(
            24,
            "Threat Hunting is best described as:",
            listOf(
                "Automatically blocking websites",
                "Updating antivirus software",
                "Searching proactively for hidden threats",
                "Creating firewall rules"
            ),
            2
        ),

        Question(
            25,
            "Which technique is commonly used to understand how malware behaves?",
            listOf(
                "Malware Analysis",
                "Password Cracking",
                "Data Compression",
                "DNS Configuration"
            ),
            0
        ),

        Question(
            26,
            "Reverse Engineering is mainly performed to:",
            listOf(
                "Improve internet speed",
                "Understand how software or malware works",
                "Recover deleted photos",
                "Increase storage space"
            ),
            1
        ),

        Question(
            27,
            "A Security Operations Center (SOC) is responsible for:",
            listOf(
                "Developing mobile applications",
                "Managing databases",
                "Monitoring and responding to security incidents",
                "Repairing computer hardware"
            ),
            2
        ),

        Question(
            28,
            "Which security solution continuously monitors endpoints for suspicious activities?",
            listOf(
                "VPN",
                "Endpoint Detection and Response (EDR)",
                "DHCP",
                "FTP"
            ),
            1
        ),

        Question(
            29,
            "In cloud security, IAM stands for:",
            listOf(
                "Internet Access Manager",
                "Identity and Access Management",
                "Integrated Application Monitoring",
                "Internal Authentication Method"
            ),
            1
        ),

        Question(
            30,
            "A Supply Chain Attack mainly targets:",
            listOf(
                "Trusted third-party vendors or software providers",
                "Physical keyboards",
                "Wi-Fi passwords",
                "Computer monitors"
            ),
            0
        ),

            Question(
                31,
                "Which authentication method is commonly used to secure REST APIs?",
                listOf(
                    "Telnet",
                    "JWT (JSON Web Token)",
                    "FTP",
                    "POP3"
                ),
                1
            ),

            Question(
                32,
                "OAuth 2.0 is primarily used for:",
                listOf(
                    "Secure authorization without sharing passwords",
                    "Encrypting hard drives",
                    "Managing firewalls",
                    "Scanning malware"
                ),
                0
            ),

            Question(
                33,
                "Which OWASP project focuses specifically on API security risks?",
                listOf(
                    "OWASP Mobile Top 10",
                    "OWASP Testing Guide",
                    "OWASP API Security Top 10",
                    "OWASP ASVS"
                ),
                2
            ),

            Question(
                34,
                "Which practice improves Docker container security?",
                listOf(
                    "Running containers as the root user",
                    "Using official and trusted container images",
                    "Disabling logging",
                    "Opening all network ports"
                ),
                1
            ),

            Question(
                35,
                "In Kubernetes, Secrets are mainly used to:",
                listOf(
                    "Store sensitive information like passwords and API keys",
                    "Increase CPU performance",
                    "Monitor network speed",
                    "Replace firewalls"
                ),
                0
            ),

            Question(
                36,
                "A ransomware attack has encrypted all company files. What should be the FIRST response?",
                listOf(
                    "Pay the ransom immediately",
                    "Disconnect affected systems from the network",
                    "Delete all backups",
                    "Ignore the incident"
                ),
                1
            ),

            Question(
                37,
                "Why is log analysis important in cybersecurity?",
                listOf(
                    "It improves internet speed",
                    "It helps detect suspicious activities and investigate incidents",
                    "It increases RAM",
                    "It removes malware automatically"
                ),
                1
            ),

            Question(
                38,
                "What is the main principle of Zero Trust Architecture?",
                listOf(
                    "Trust all internal users",
                    "Trust only administrators",
                    "Never trust, always verify",
                    "Allow anonymous access"
                ),
                2
            ),

            Question(
                39,
                "A CVE (Common Vulnerabilities and Exposures) identifier is used to:",
                listOf(
                    "Identify publicly known security vulnerabilities",
                    "Measure internet bandwidth",
                    "Generate encryption keys",
                    "Store passwords"
                ),
                0
            ),

            Question(
                40,
                "CVSS is mainly used to:",
                listOf(
                    "Create digital certificates",
                    "Calculate the severity of security vulnerabilities",
                    "Manage cloud storage",
                    "Encrypt databases"
                ),
                1
            ),

            Question(
                41,
                "Artificial Intelligence (AI) helps cybersecurity teams by:",
                listOf(
                    "Automatically detecting suspicious activities and threats",
                    "Replacing all security professionals",
                    "Disabling firewalls",
                    "Increasing internet speed"
                ),
                0
            ),

            Question(
                42,
                "Threat Intelligence is primarily used to:",
                listOf(
                    "Create viruses",
                    "Collect and analyze information about cyber threats",
                    "Increase storage capacity",
                    "Manage printers"
                ),
                1
            ),

            Question(
                43,
                "During a digital forensic investigation, what is the FIRST priority?",
                listOf(
                    "Delete suspicious files",
                    "Restart the computer",
                    "Preserve digital evidence",
                    "Format the hard drive"
                ),
                2
            ),

            Question(
                44,
                "Which responsibility belongs to a SOC (Security Operations Center) Analyst?",
                listOf(
                    "Design company logos",
                    "Repair computer hardware",
                    "Develop mobile applications",
                    "Monitor and respond to security alerts"
                ),
                3
            ),

            Question(
                45,
                "The MITRE ATT&CK Framework is mainly used to:",
                listOf(
                    "Understand attacker tactics and techniques",
                    "Manage cloud storage",
                    "Increase CPU performance",
                    "Create encryption keys"
                ),
                0
            ),

            Question(
                46,
                "Which phase of the Cyber Kill Chain represents gaining access to the target system?",
                listOf(
                    "Reconnaissance",
                    "Exploitation",
                    "Installation",
                    "Actions on Objectives"
                ),
                1
            ),

            Question(
                47,
                "Which Secure Coding practice helps prevent SQL Injection attacks?",
                listOf(
                    "Using plain SQL strings",
                    "Disabling authentication",
                    "Using parameterized queries",
                    "Removing input validation"
                ),
                2
            ),

            Question(
                48,
                "Which data classification level requires the highest level of protection?",
                listOf(
                    "Public",
                    "Internal",
                    "Confidential",
                    "Top Secret"
                ),
                3
            ),

            Question(
                49,
                "What is the primary goal of a Business Continuity Plan (BCP)?",
                listOf(
                    "Ensure critical business operations continue during disruptions",
                    "Increase internet speed",
                    "Reduce electricity usage",
                    "Replace antivirus software"
                ),
                0
            ),

            Question(
                50,
                "Disaster Recovery (DR) mainly focuses on:",
                listOf(
                    "Preventing phishing emails",
                    "Restoring systems and data after a disaster",
                    "Writing secure code",
                    "Blocking websites"
                ),
                1
            )

        )

    }

}
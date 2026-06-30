package com.example.cyberpath.repository

import com.example.cyberpath.model.Scenario

object ScenarioRepository {

    fun getScenarios(): List<Scenario> {

        return listOf(

            Scenario(

                id = 1,

                title = "WhatsApp Emergency Scam",

                type = "WhatsApp",

                message =
                    "Hi Dad, I lost my phone. This is my new number. Please send ₹15,000 immediately. I'll return it tomorrow.",

                warningSigns = listOf(

                    "Unknown number",

                    "Creates urgency",

                    "Requests money",

                    "No identity verification"

                ),

                options = listOf(

                    "Send ₹15,000 immediately",

                    "Reply asking for bank details",

                    "Call the person's original number before taking any action",

                    "Share your UPI PIN"

                ),

                correctAnswer = 2,

                explanation =
                    "Always verify the sender using their previously known phone number before sending money or sharing information.",

                cyberTip =
                    "Scammers often impersonate family members and create fake emergencies to pressure victims."

            ),

            Scenario(

                id = 2,

                title = "Fake Bank Verification Call",

                type = "Phone Call",

                message =
                    "You receive a call claiming to be from your bank. The caller says your account will be blocked unless you immediately provide your debit card number, CVV, and OTP.",

                warningSigns = listOf(

                    "Requests confidential information",

                    "Creates panic",

                    "Demands OTP",

                    "Unknown caller"

                ),

                options = listOf(

                    "Hang up immediately and contact the bank using its official customer care number",

                    "Share only your debit card number",

                    "Share your OTP",

                    "Provide your CVV because the caller knows your name"

                ),

                correctAnswer = 0,

                explanation =
                    "Banks never ask customers for OTPs, PINs, passwords, or CVV numbers over phone calls.",

                cyberTip =
                    "If you're unsure, disconnect the call and contact your bank through its official phone number or mobile app."

            ),

            Scenario(

                id = 3,

                title = "OTP Verification Scam",

                type = "SMS",

                message =
                    "You receive a phone call from someone claiming to be from your mobile service provider. They say your SIM card will be deactivated within 30 minutes unless you immediately share the OTP sent to your phone.",

                warningSigns = listOf(

                    "Requests OTP",

                    "Creates urgency",

                    "Threatens SIM deactivation",

                    "Unknown caller"

                ),

                options = listOf(

                    "Read only the first four digits of the OTP",

                    "Forward the OTP message",

                    "Ignore the warning and continue sharing personal information",

                    "Do not share the OTP and contact the service provider through official customer support"

                ),

                correctAnswer = 3,

                explanation =
                    "OTPs are confidential authentication codes. Genuine telecom companies will never ask customers to reveal them over a phone call.",

                cyberTip =
                    "Never share OTPs with anyone. They are meant only for the account owner."

            ),

            Scenario(

                id = 4,

                title = "QR Code Payment Scam",

                type = "UPI",

                message =
                    "A buyer agrees to purchase your old laptop online. They say they have already sent the payment and ask you to scan a QR code to 'receive' the money.",

                warningSigns = listOf(

                    "Unknown QR code",

                    "Claims scanning receives money",

                    "Unverified buyer",

                    "Creates confusion"

                ),

                options = listOf(

                    "Scan the QR code immediately",

                    "Refuse to scan the QR code and ask the buyer to transfer the payment directly to your UPI ID",

                    "Share your UPI PIN with the buyer",

                    "Install another payment application suggested by the buyer"

                ),

                correctAnswer = 1,

                explanation =
                    "Scanning a QR code generally authorizes a payment from your account. Receiving money never requires scanning someone else's QR code.",

                cyberTip =
                    "To receive money, simply share your UPI ID or registered mobile number. Never scan unknown QR codes."

            ),

            Scenario(

                id = 5,

                title = "Fake Job Offer Scam",

                type = "Email",

                message =
                    "Congratulations! You have been selected for a work-from-home job with a salary of ₹80,000 per month. To receive your offer letter, pay a refundable registration fee of ₹2,500 within the next hour.",

                warningSigns = listOf(

                    "Requests money before joining",

                    "Unrealistically high salary",

                    "No interview process",

                    "Creates urgency"

                ),

                options = listOf(

                    "Pay the registration fee immediately",

                    "Share your Aadhaar card first",

                    "Send your bank account details",

                    "Verify the company through its official website and never pay for a job offer"

                ),

                correctAnswer = 3,

                explanation =
                    "Legitimate companies never ask candidates to pay money for interviews, registrations, or offer letters.",

                cyberTip =
                    "Always verify job offers through the company's official website or HR department."

            ),

            Scenario(

                id = 6,

                title = "Courier Delivery Scam",

                type = "SMS",

                message =
                    "You receive an SMS saying: 'Your parcel delivery has failed due to an incorrect address. Pay ₹50 using the link below to reschedule your delivery.'",

                warningSigns = listOf(

                    "Unexpected payment request",

                    "Unknown website link",

                    "Generic SMS",

                    "Creates urgency"

                ),

                options = listOf(

                    "Click the link and pay ₹50",

                    "Ignore the message and verify your parcel status using the courier company's official app or website",

                    "Reply with your home address",

                    "Forward the SMS to your friends"

                ),

                correctAnswer = 1,

                explanation =
                    "Scammers frequently send fake courier messages to steal banking credentials or payment information through phishing websites.",

                cyberTip =
                    "Track your deliveries only through the courier company's official website or mobile application."

            ),

            Scenario(

                id = 7,

                title = "Fake Tech Support Scam",

                type = "Computer",

                message =
                    "While browsing the internet, a full-screen pop-up appears saying: '⚠ Your computer is infected with 18 viruses! Call Microsoft Support immediately or your files will be deleted.'",

                warningSigns = listOf(

                    "Scary full-screen warning",

                    "Requests immediate action",

                    "Fake Microsoft support",

                    "Threatens data loss"

                ),

                options = listOf(

                    "Call the support number shown on the screen",

                    "Close the browser, ignore the pop-up, and scan your computer using trusted antivirus software",

                    "Download the software recommended by the pop-up",

                    "Give remote access to your computer"

                ),

                correctAnswer = 1,

                explanation =
                    "Legitimate companies like Microsoft do not display browser pop-ups asking users to call support numbers or grant remote access.",

                cyberTip =
                    "Never trust browser pop-ups asking for payment or remote access. Close the browser and use trusted security software."

            ),

            Scenario(

                id = 8,

                title = "CEO Fraud Email",

                type = "Email",

                message =
                    "You receive an email that appears to be from your company's CEO asking you to urgently purchase ₹50,000 worth of gift cards and send the activation codes by email.",

                warningSigns = listOf(

                    "Unexpected financial request",

                    "Creates urgency",

                    "Unusual communication",

                    "Requests gift cards"

                ),

                options = listOf(

                    "Buy the gift cards immediately",

                    "Verify the request directly with your manager or CEO before taking any action",

                    "Reply asking for your salary increase",

                    "Forward the email to everyone"

                ),

                correctAnswer = 1,

                explanation =
                    "CEO fraud (Business Email Compromise) relies on impersonating senior executives to trick employees into sending money or sensitive information.",

                cyberTip =
                    "Always verify urgent financial requests through an official communication channel before acting."

            ),

            Scenario(

                id = 9,

                title = "Facebook Friend Scam",

                type = "Social Media",

                message =
                    "Late at night, you receive a Facebook Messenger message from your friend saying: 'I'm stuck at the airport. Please send ₹8,000 immediately. I'll pay you back tomorrow.'",

                warningSigns = listOf(

                    "Unexpected money request",

                    "Creates urgency",

                    "No phone call",

                    "Possible hacked account"

                ),

                options = listOf(

                    "Send the money immediately",

                    "Reply asking for their bank details",

                    "Share your debit card details instead",

                    "Call your friend using their saved phone number to verify the request before sending money"

                ),

                correctAnswer = 3,

                explanation =
                    "Many Facebook and Instagram accounts are compromised by attackers. Always verify emergency requests using another trusted communication method.",

                cyberTip =
                    "Never send money based only on a social media message. Verify the person's identity first."

            ),

            Scenario(

                id = 10,

                title = "UPI Refund Scam",

                type = "Digital Payment",

                message =
                    "Someone calls claiming they accidentally transferred ₹5,000 to your account. They ask you to click a payment link to 'return' the money immediately.",

                warningSigns = listOf(

                    "Unknown caller",

                    "Requests immediate payment",

                    "Unknown payment link",

                    "Creates urgency"

                ),

                options = listOf(

                    "Verify your bank statement first and use only your bank or official UPI app if a genuine refund is required",

                    "Click the payment link immediately",

                    "Share your UPI PIN",

                    "Install another payment application"

                ),

                correctAnswer = 0,

                explanation =
                    "Scammers often send fake payment screenshots or malicious payment links. Always verify your transaction history before sending money.",

                cyberTip =
                    "Never trust payment screenshots or unknown payment links. Check your actual bank balance or UPI transaction history."

            )

        )

    }

}
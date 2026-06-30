package com.example.cyberpath.data

import com.example.cyberpath.models.EmailModel

object EmailRepository {

    val emails = listOf(

        // ======================================================
        // EMAIL 1 (PHISHING)
        // ======================================================

        EmailModel(

            sender = "security-alert@paypal-secure.net",

            subject = "⚠ Your account has been compromised!",

            body = """
Dear Customer,

We detected suspicious activity in your PayPal account.

Your account will be suspended within 24 hours unless you verify your identity immediately.

Click below:

http://paypal-verify-account.xyz/secure

Thank you,
PayPal Security Team
            """.trimIndent(),

            isPhishing = true,

            explanation = listOf(

                "Fake PayPal domain",

                "Creates urgency",

                "Suspicious verification link",

                "Generic greeting"

            )

        ),

        // ======================================================
        // EMAIL 2 (SAFE)
        // ======================================================

        EmailModel(

            sender = "newsletter@medium.com",

            subject = "Your Weekly Reading List is Ready",

            body = """
Hi Alex,

Here are your personalized story recommendations based on your interests.

Enjoy reading!

Thanks,
Medium Team
            """.trimIndent(),

            isPhishing = false,

            explanation = listOf(

                "Official Medium domain",

                "No urgency",

                "No password request",

                "Normal newsletter"

            )

        ),

        // ======================================================
        // EMAIL 3 (PHISHING)
        // ======================================================

        EmailModel(

            sender = "kyc-update@sbi-security.co",

            subject = "Urgent: Complete Your SBI KYC Verification",

            body = """
Dear Customer,

Your SBI account will be permanently blocked if your KYC is not updated today.

Verify here:

http://sbi-kyc-update.xyz

Regards,
SBI Verification Team
            """.trimIndent(),

            isPhishing = true,

            explanation = listOf(

                "Fake SBI domain",

                "HTTP link",

                "Threatens account closure",

                "Creates urgency"

            )

        ),

        // ======================================================
        // EMAIL 4 (SAFE)
        // ======================================================

        EmailModel(

            sender = "shipment-tracking@amazon.in",

            subject = "Your Amazon Order Has Been Shipped",

            body = """
Hello,

Your recent Amazon order has been shipped.

Track your package anytime using the Amazon mobile app or website.

Thank you for shopping with Amazon.

Amazon India
            """.trimIndent(),

            isPhishing = false,

            explanation = listOf(

                "Official Amazon domain",

                "No urgent request",

                "No password requested",

                "Typical shipment notification"

            )

        ),

        // ======================================================
        // EMAIL 5 (PHISHING)
        // ======================================================

        EmailModel(

            sender = "security@microsoft-support365.net",

            subject = "Microsoft Password Reset Required",

            body = """
Dear User,

We detected multiple failed login attempts on your Microsoft account.

Your account will be locked within 12 hours unless you reset your password immediately.

Reset Password:
http://microsoft-password-reset.xyz

Microsoft Security Team
            """.trimIndent(),

            isPhishing = true,

            explanation = listOf(

                "Fake Microsoft domain",

                "HTTP phishing link",

                "Creates urgency",

                "Requests password reset through unofficial website"

            )

        ),

        // ======================================================
        // EMAIL 6 (SAFE)
        // ======================================================

        EmailModel(

            sender = "no-reply@accounts.google.com",

            subject = "New Sign-in to Your Google Account",

            body = """
Hi,

A new sign-in to your Google Account was detected.

If this was you, no action is required.

If you don't recognize this activity, review your account security from your Google Account settings.

Google Accounts
            """.trimIndent(),

            isPhishing = false,

            explanation = listOf(

                "Official Google domain",

                "Does not ask for passwords",

                "No threatening language",

                "Advises checking account settings"

            )

        ),

        // ======================================================
        // EMAIL 7 (PHISHING)
        // ======================================================

        EmailModel(

            sender = "refund@income-tax-gov.in.net",

            subject = "Income Tax Refund Available",

            body = """
Dear Taxpayer,

Your Income Tax refund of ₹18,750 is pending.

To receive the refund, verify your PAN and bank account immediately.

Claim Refund:

http://income-tax-refund.xyz

Income Tax Department
            """.trimIndent(),

            isPhishing = true,

            explanation = listOf(

                "Fake government domain",

                "Suspicious refund link",

                "Requests sensitive information",

                "Creates urgency"

            )

        ),

        // ======================================================
        // EMAIL 8 (SAFE)
        // ======================================================

        EmailModel(

            sender = "messages-noreply@linkedin.com",

            subject = "You Have a New Connection Request",

            body = """
Hello,

Someone has sent you a connection request on LinkedIn.

Visit your LinkedIn account to review and respond.

Thank you,

LinkedIn Team
            """.trimIndent(),

            isPhishing = false,

            explanation = listOf(

                "Official LinkedIn domain",

                "No urgency",

                "No password request",

                "Normal notification email"

            )

        ),

        // ======================================================
        // EMAIL 9 (PHISHING)
        // ======================================================

        EmailModel(

            sender = "billing@netflix-support.co",

            subject = "Your Netflix Payment Failed",

            body = """
Dear Customer,

We were unable to process your latest Netflix subscription payment.

Your account will be suspended within 24 hours unless you update your payment information.

Update Payment:

http://netflix-payment-update.xyz

Netflix Billing Team
            """.trimIndent(),

            isPhishing = true,

            explanation = listOf(

                "Fake Netflix domain",

                "HTTP phishing link",

                "Creates urgency",

                "Requests payment details"

            )

        ),

        // ======================================================
        // EMAIL 10 (SAFE)
        // ======================================================

        EmailModel(

            sender = "notifications@github.com",

            subject = "New Activity in Your Repository",

            body = """
Hello,

There has been new activity in one of your GitHub repositories.

You can review the latest commits, pull requests, or issues by visiting your GitHub account.

Thank you,

GitHub Notifications
            """.trimIndent(),

            isPhishing = false,

            explanation = listOf(

                "Official GitHub domain",

                "No urgency or threats",

                "Does not request passwords or OTPs",

                "Normal repository notification"

            )

        )

    )

}
package com.example.cyberpath.utils

import com.example.cyberpath.R
import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import com.example.cyberpath.model.Certificate
import java.io.File
import java.io.FileOutputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream

object PdfGenerator {

    fun generateCertificate(
        context: Context,
        certificate: Certificate
    ): File {

        val document = PdfDocument()

        val pageInfo = PdfDocument.PageInfo.Builder(
            1200,
            1700,
            1
        ).create()

        val page = document.startPage(pageInfo)

        val canvas = page.canvas

        // Background
        canvas.drawColor(Color.parseColor("#0B1220"))

        val watermarkPaint = Paint().apply {

            color = Color.GRAY

            alpha = 30

            textSize = 140f

            textAlign = Paint.Align.CENTER

            typeface = Typeface.DEFAULT_BOLD

        }

        canvas.save()

        canvas.rotate(
            -35f,
            600f,
            850f
        )

        canvas.drawText(
            "CYBERPATH",
            600f,
            850f,
            watermarkPaint
        )

        canvas.restore()

        // Gold Border
        val borderPaint = Paint().apply {
            color = Color.parseColor("#FFC107")
            style = Paint.Style.STROKE
            strokeWidth = 6f
        }

        // Outer Border
        canvas.drawRect(
            40f,
            40f,
            1160f,
            1660f,
            borderPaint
        )

// Inner Border
        canvas.drawRect(
            70f,
            70f,
            1130f,
            1630f,
            borderPaint
        )

        val logo = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.cyberpath_logo
        )

        if (logo != null) {

            val scaledLogo = Bitmap.createScaledBitmap(
                logo,
                120,
                120,
                true
            )

            canvas.drawBitmap(
                scaledLogo,
                540f,
                25f,
                null
            )
        }

        // Title
        val titlePaint = Paint().apply {
            color = Color.parseColor("#FFC107")
            textSize = 52f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        canvas.drawText(
            "CYBERPATH ACADEMY",
            600f,
            220f,
            titlePaint
        )

        // Certificate Heading
        val headingPaint = Paint().apply {
            color = Color.WHITE
            textSize = 64f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        canvas.drawText(
            "Certificate of Completion",
            600f,
            290f,
            headingPaint
        )

        val normalPaint = Paint().apply {
            color = Color.LTGRAY
            textSize = 34f
            textAlign = Paint.Align.CENTER
        }

        canvas.drawText(
            "This certificate is proudly presented to",
            600f,
            390f,
            normalPaint
        )

        val namePaint = Paint().apply {
            color = Color.WHITE
            textSize = 60f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        canvas.drawText(
            certificate.userName,
            600f,
            500f,
            namePaint
        )

        canvas.drawText(
            "For successfully completing",
            600f,
            610f,
            normalPaint
        )

        val coursePaint = Paint().apply {
            color = Color.parseColor("#00C2FF")
            textSize = 44f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        canvas.drawText(
            "Cyber Security Awareness",
            600f,
            710f,
            coursePaint
        )

        canvas.drawText(
            "& Practical Training",
            600f,
            770f,
            coursePaint
        )

        val infoPaint = Paint().apply {
            color = Color.WHITE
            textSize = 34f
        }

        canvas.drawText(
            "Certificate ID : ${certificate.certificateId}",
            120f,
            980f,
            infoPaint
        )

        canvas.drawText(
            "Best Quiz Score : ${certificate.bestQuizScore}%",
            120f,
            1050f,
            infoPaint
        )

        canvas.drawText(
            "Issue Date : ${certificate.issueDate}",
            120f,
            1120f,
            infoPaint
        )

        canvas.drawLine(
            160f,
            1410f,
            420f,
            1410f,
            borderPaint
        )

        canvas.drawText(
            "Instructor",
            220f,
            1455f,
            infoPaint
        )

        canvas.drawText(
            "CyberPath Academy",
            170f,
            1505f,
            infoPaint
        )

        document.finishPage(page)

        val safeName = certificate.userName
            .replace(" ", "_")
            .replace("[^A-Za-z0-9_]".toRegex(), "")

        val fileName =
            "CyberPath_${safeName}_${certificate.certificateId}.pdf"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val resolver = context.contentResolver

            val values = ContentValues().apply {

                put(
                    MediaStore.Downloads.DISPLAY_NAME,
                    fileName
                )

                put(
                    MediaStore.Downloads.MIME_TYPE,
                    "application/pdf"
                )

                put(
                    MediaStore.Downloads.RELATIVE_PATH,
                    Environment.DIRECTORY_DOWNLOADS + "/CyberPath"
                )

            }

            val uri = resolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                values
            ) ?: throw Exception("Unable to create file.")

            val outputStream: OutputStream =
                resolver.openOutputStream(uri)
                    ?: throw Exception("Unable to open output stream.")

            document.writeTo(outputStream)

            outputStream.flush()
            outputStream.close()

            document.close()

            return File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ),
                "CyberPath/$fileName"
            )

        } else {

            val directory = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ),
                "CyberPath"
            )

            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)

            document.writeTo(FileOutputStream(file))

            document.close()

            return file

        }
    }
}
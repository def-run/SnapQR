const downloadBtn = document.getElementById("download-btn");
const contentInput = document.getElementById("content");
const sizeInput = document.getElementById("qr-size");

const foregroundPicker = Pickr.create({
    el: '#foreground-picker',
    theme: 'classic',
    default: '#000000',

    components: {
        preview: true,
        opacity: false,
        hue: true,

        interaction: {
            hex: true,
            rgba: true,
            input: true,
            save: true
        }
    }
});

const backgroundPicker = Pickr.create({
    el: '#background-picker',
    theme: 'classic',
    default: '#3A4E48',

    components: {
        preview: true,
        opacity: false,
        hue: true,

        interaction: {
            hex: true,
            rgba: true,
            input: true,
            save: true
        }
    }
});

const form = document.getElementById("qr-form");

let qrObjectUrl = null;

form.addEventListener("submit", async function (event) {

    event.preventDefault();

    const content = contentInput.value;
    const foregroundColor = foregroundPicker.getColor().toHEXA().toString();
    const backgroundColor = backgroundPicker.getColor().toHEXA().toString();
    const size = sizeInput.value;

    const requestData = {
        content: content,
        size: Number(size),
        foregroundColor: foregroundColor,
        backgroundColor: backgroundColor
    };

    try {
        const response = await fetch("/api/qr/static", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "QR generation failed");
        }

        const imageBlob = await response.blob();

        if (qrObjectUrl) {
            URL.revokeObjectURL(qrObjectUrl);
        }

        qrObjectUrl = URL.createObjectURL(imageBlob);

        const qrImage = document.getElementById("qr-image");
        const placeholder = document.getElementById("preview-placeholder");

        qrImage.src = qrObjectUrl;
        qrImage.style.display = "block";
        placeholder.style.display = "none";
        downloadBtn.disabled = false;

        console.log("QR code generated successfully");

    } catch (error) {
        console.error("Error:", error.message);
        alert(error.message);
    }
});

downloadBtn.addEventListener("click", function () {

    const link = document.createElement("a");

    link.href = qrObjectUrl;
    link.download = "SnapQR.png";

    link.click();

});
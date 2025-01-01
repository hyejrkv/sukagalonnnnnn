function handleResetPassword(event) {
    event.preventDefault(); 
    const email = document.getElementById('email').value;
    document.getElementById('modalMessage').innerText = 
        `Tautan reset password berhasil dikirim ke ${email}`;
    document.getElementById('alertModal').style.display = 'block';

    // Simulate server action (replace this with an actual API call)
    setTimeout(() => {
        closeModal();
    }, 3000);
}

function closeModal() {
    document.getElementById('alertModal').style.display = 'none';
}

/*
async function handleResetPassword(event) {
    event.preventDefault();

    const email = document.getElementById('resetEmail').value.trim();

    if (!email) {
        showModal('Mohon masukkan email Anda!');
        return;
    }

    try {
        // Kirim permintaan ke API untuk memulai proses reset password
        const response = await fetch('/api/reset-password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email }),
        });

        const data = await response.json();

        if (response.ok && data.status === 'success') {
            showModal(`Tautan reset password berhasil dikirim ke ${email}`);
        } else {
            showModal(data.message || 'Gagal mengirim tautan reset password.');
        }
    } catch (error) {
        console.error('Error:', error);
        showModal('Terjadi kesalahan. Silakan coba lagi nanti.');
    }
}

function showModal(message) {
    const modal = document.getElementById('alertModal');
    const modalMessage = document.getElementById('modalMessage');
    modalMessage.textContent = message;
    modal.style.display = 'flex';
}

function closeModal() {
    const modal = document.getElementById('alertModal');
    modal.style.display = 'none';
}
*/

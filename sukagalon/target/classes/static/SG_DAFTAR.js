/*document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');
    const modal = document.getElementById('successModal');

    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        // Ambil nilai input dari form
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // Validasi email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert('Format email tidak valid!');
            return;
        }

        // Validasi password
        if (password.length < 6) {
            alert('Password harus minimal 6 karakter!');
            return;
        }
        
        if (password !== confirmPassword) {
            alert('Password dan konfirmasi password tidak cocok!');
            return;
        }
        // Kirim data ke server
        try {
            const response = await fetch('/Daftar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    email: email,
                    password: password,
                    confirmPassword: confirmPassword
                }).toString(),
            });

            const data = await response.json();

            if (response.ok) {
                // Jika pendaftaran berhasil, tampilkan modal
                showSuccessModal();
            } else {
                // Jika ada kesalahan, tampilkan pesan kesalahan
                //const errorData = await response.json();
                alert(errorData.message || 'Terjadi kesalahan saat mendaftar.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Terjadi kesalahan saat menghubungi server.');
        }
    });

    // Fungsi untuk menampilkan modal
    function showSuccessModal() {
        modal.style.display = 'flex';

        // Redirect ke halaman login setelah 2 detik
        setTimeout(function() {
            window.location.href = '/Login';
        }, 2000);
    }

    // Tutup modal jika user klik di luar modal
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
});

*/
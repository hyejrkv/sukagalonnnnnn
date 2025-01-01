// Simulasi data pengguna yang terdaftar di database
/*const usersDatabase = [
    { email: "admin@sukagalon.com", password: "admin123" },
    { email: "user1@example.com", password: "userpass1" }
];*/

// Fungsi untuk menangani login
async function handleLogin(event) {
    event.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const remember = document.getElementById('remember').checked;
    
    try {
        const response = await fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`
        });
        
        const data = await response.json();
        
        if (remember) {
            localStorage.setItem('userEmail', email);
        } else {
            localStorage.removeItem('userEmail');
        }

        if (response.ok) {
            // Show success message
            showAlert('Login berhasil!');
            
            // Redirect after showing alert
            setTimeout(() => {
                window.location.href = data.redirectUrl;
            }, 1500);
        } else {
            showAlert(data.error || 'Login gagal. Silakan coba lagi.');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan. Silakan coba lagi.');
    }
}
/*
function handleLogin(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const remember = document.getElementById('remember').checked;

    // Validasi input
    if (!email || !password) {
        showModal('Mohon isi semua field!');
        return;
    }
    
    // Validasi pengguna berdasarkan database
    const user = usersDatabase.find(user => user.email === email);
    if (!user) {
        showModal('Email tidak terdaftar!');
        return;
    }

    if (user.password !== password) {
        showModal('Password salah!');
        return;
    }

    // Jika berhasil login, simpan email jika opsi 'remember' dipilih
    if (remember) {
        localStorage.setItem('userEmail', email);
    } else {
        localStorage.removeItem('userEmail');
    }

    // Tampilkan pesan sukses dan redirect berdasarkan peran
    showModal('Login berhasil!');
    setTimeout(() => {
        if (email === "admin@sukagalon.com") {
            window.location.href = 'SG_DASHBOARD-ADMIN.html';
        } else {
            window.location.href = '/sukagalon/src/main/java/SG_BELANJA.html'; // Redirect untuk pengguna biasa
        }
    }, 1500);
}
*/
// Fungsi untuk menampilkan modal pesan
function showModal(message) {
    const modal = document.getElementById('alertModal');
    const modalMessage = document.getElementById('modalMessage');
    modalMessage.textContent = message;
    modal.style.display = 'flex';
}

// Fungsi untuk menutup modal pesan
function closeModal() {
    const modal = document.getElementById('alertModal');
    modal.style.display = 'none';
}

// Toggle visibilitas password
function togglePassword() {
    const passwordInput = document.getElementById('password');
    const toggleBtn = document.querySelector('.toggle-password');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleBtn.textContent = '👁️';
    } else {
        passwordInput.type = 'password';
        toggleBtn.textContent = '👁️‍🗨️';
    }
}

// Menyimpan email yang diingat
document.addEventListener('DOMContentLoaded', () => {
    const savedEmail = localStorage.getItem('userEmail');
    if (savedEmail) {
        document.getElementById('email').value = savedEmail;
        document.getElementById('remember').checked = true;
    }
});

// Menutup modal jika mengklik di luar modal
window.onclick = function(event) {
    const modal = document.getElementById('alertModal');
    if (event.target === modal) {
        closeModal();
    }
};

// Menutup modal dengan tombol Escape
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        closeModal();
    }
});
/*
async function handleLogin(event) {
    event.preventDefault(); // Mencegah reload halaman

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:8080/api/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const message = await response.text();
            alert(message); // Menampilkan pesan sukses
            window.location.href = "SG_BELANJA.html"; // Redirect ke halaman belanja
        } else {
            const errorMessage = await response.text();
            alert(errorMessage); // Menampilkan pesan gagal
        }
    } catch (error) {
        console.error("Error:", error);
        alert("Terjadi kesalahan saat login.");
    }
}
*/
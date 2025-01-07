// Data structure example (akan diisi dari database)
let purchaseHistory = [];

function toggleProfileMenu() {
    const dropdown = document.getElementById('profileDropdown');
    dropdown.classList.toggle('show');  // Toggle class 'show' untuk menampilkan dropdown
}

// Utility Functions
const formatDate = (date) => {
    return new Date(date).toLocaleDateString('id-ID', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
};

const formatCurrency = (amount) => {
    return new Intl.NumberFormat('id-ID', {
        style: 'currency',
        currency: 'IDR'
    }).format(amount);
};

// Mengambil email pengguna dan menampilkannya di profile
function displayUserEmail() {
    const email = localStorage.getItem('userEmail');
    const profileEmailElement = document.getElementById('profileEmail');
    if (email && profileEmailElement) {
        profileEmailElement.textContent = email;
    }
}

// Menjalankan displayUserEmail saat halaman dimuat
window.onload = function() {
    displayUserEmail();  // Tampilkan email pengguna di profil
    const purchaseViewer = new PurchaseHistoryViewer();
};

// Class PurchaseHistoryViewer (tetap sama)
class PurchaseHistoryViewer {
    constructor() {
        this.tableBody = document.querySelector('.history-table tbody');
        this.loadingIndicator = document.querySelector('.loading-indicator');
        this.adminUpdateButton = document.querySelector('#updateHistoryByAdmin'); // Button for admin updates
        this.initialize();
    }

    initialize() {
        this.loadData();

        // Refresh button functionality
        const refreshButton = document.querySelector('#refreshHistory');
        if (refreshButton) {
            refreshButton.addEventListener('click', () => this.loadData());
        }

        // Admin update functionality
        if (this.adminUpdateButton) {
            this.adminUpdateButton.addEventListener('click', () => this.updateByAdmin());
        }
    }
/*
    async loadData() {
        try {
            if (this.loadingIndicator) {
                this.loadingIndicator.style.display = 'block';
            }

            // Simulate API call to fetch purchase history (Replace with actual API endpoint)
            // const response = await fetch('/api/user/purchase-history');
            // purchaseHistory = await response.json();

            // Temporary simulation data (Remove when connecting to real API)
            await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate loading
            purchaseHistory = [
                { productName: 'Galon Aqua', purchaseDate: '2024-12-20', price: 30000, paymentStatus: 'SUCCESS' },
                { productName: 'Galon Le Minerale', purchaseDate: '2024-12-21', price: 28000, paymentStatus: 'SUCCESS' }
            ];

            this.renderTable();
        } catch (error) {
            console.error('Error loading data:', error);
            this.showError('Gagal memuat data. Silakan coba lagi nanti.');
        } finally {
            if (this.loadingIndicator) {
                this.loadingIndicator.style.display = 'none';
            }
        }
    }
*/
    renderTable() {
        if (!this.tableBody) return;

        if (purchaseHistory.length === 0) {
            this.tableBody.innerHTML = `
                <tr>
                    <td colspan="5" class="empty-state">
                        <div class="empty-state-message">
                            Belum ada transaksi apapun
                        </div>
                    </td>
                </tr>`;
            return;
        }

        this.tableBody.innerHTML = purchaseHistory.map((item, index) => `
            <tr>
                <td>${index + 1}</td>
                <td>${item.productName}</td>
                <td>${formatDate(item.purchaseDate)}</td>
                <td>${formatCurrency(item.price)}</td>
                <td>
                    <span class="status ${item.paymentStatus.toLowerCase()}">
                        ${item.paymentStatus === 'SUCCESS' ? 'Berhasil' : 'Gagal'}
                    </span>
                </td>
            </tr>
        `).join('');
    }

    showError(message) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.textContent = message;

        const existingError = document.querySelector('.error-message');
        if (existingError) {
            existingError.remove();
        }

        document.querySelector('.history-container').prepend(errorDiv);

        setTimeout(() => {
            errorDiv.remove();
        }, 5000);
    }

    // Function to handle admin updates
    updateByAdmin() {
        // Simulate admin update functionality
        const newEntry = {
            productName: 'Galon Vit',
            purchaseDate: new Date().toISOString(),
            price: 25000,
            paymentStatus: 'SUCCESS'
        };

        purchaseHistory.push(newEntry);
        this.renderTable();

        alert('Riwayat pembelian berhasil diperbarui oleh admin.');
    }
}
let cart = [];

// Data produk termasuk stok
const products = [
    { name: 'Le Minerale 15L', price: 22000, image: 'le minerale.jpeg', stock: 10 },
    { name: 'Le Minerale 5L', price: 15000, image: 'le minerale 5l.jpg', stock: 8 },
    { name: 'Cleo - 19L', price: 34000, image: 'CLEO.jpeg', stock: 5 },
    { name: 'Cleo 6L', price: 19000, image: 'cleo MINI.png', stock: 12 },
    { name: 'Crystalline 6L', price: 14000, image: 'Cryst.jpg', stock: 15 },
    { name: 'Crystalline 19L', price: 58000, image: 'cryst 19l.jpg', stock: 3 }
];

// Cart Functions
function toggleCart() {
    const cartPopup = document.querySelector('.cart-popup');
    const overlay = document.querySelector('.overlay');
    const isVisible = cartPopup.style.display === 'block';
    
    cartPopup.style.display = isVisible ? 'none' : 'block';
    overlay.style.display = isVisible ? 'none' : 'block';
    
    if (isVisible) {
        document.body.style.overflow = 'auto';
    } else {
        document.body.style.overflow = 'hidden';
        updateCartDisplay();
    }
}

function addToCart(name, price, image) {
    const product = products.find(item => item.name === name);
    
    // Validasi stok
    const existingItem = cart.find(item => item.name === name);
    if (existingItem) {
        if (existingItem.quantity < product.stock) {
            existingItem.quantity++;
            showNotification(`${name} ditambahkan ke keranjang`);
        } else {
            showNotification(`Stok ${name} tidak cukup!`, 'error');
            return;
        }
    } else {
        if (product.stock > 0) {
            cart.push({ name, price, image, quantity: 1 });
            showNotification(`${name} ditambahkan ke keranjang`);
        } else {
            showNotification(`Stok ${name} habis!`, 'error');
            return;
        }
    }
    
    updateCartCount();
    updateCartDisplay();
}

function removeFromCart(index) {
    const item = cart[index];
    cart.splice(index, 1);
    updateCartCount();
    updateCartDisplay();
    
    // Show notification
    showNotification(`${item.name} dihapus dari keranjang`);
}

function updateQuantity(index, change) {
    const item = cart[index];
    const product = products.find(p => p.name === item.name);
    const newQuantity = item.quantity + change;
    
    if (newQuantity > 0 && newQuantity <= product.stock) {
        item.quantity = newQuantity;
    } else if (newQuantity > product.stock) {
        showNotification(`Stok ${item.name} tidak cukup!`, 'error');
        return;
    } else {
        removeFromCart(index);
    }
    
    updateCartCount();
    updateCartDisplay();
}

function updateCartCount() {
    const cartCount = document.querySelector('.cart-count');
    const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
    cartCount.textContent = totalItems;
}

function updateCartDisplay() {
    const cartItems = document.querySelector('.cart-items');
    const cartTotal = document.querySelector('.cart-total');
    
    cartItems.innerHTML = cart.map((item, index) => `
        <div class="cart-item">
            <img src="${item.image}" alt="${item.name}">
            <div class="item-details">
                <h4>${item.name}</h4>
                <p>Rp${item.price.toLocaleString()}</p>
                <div class="quantity-controls">
                    <button class="btn" onclick="updateQuantity(${index}, -1)">-</button>
                    <span>${item.quantity}</span>
                    <button class="btn" onclick="updateQuantity(${index}, 1)">+</button>
                    <button class="btn btn-danger" onclick="removeFromCart(${index})">Hapus</button>
                </div>
            </div>
        </div>
    `).join('');

    const total = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    cartTotal.textContent = `Total: Rp${total.toLocaleString()}`;
}

function checkout() {
    if (cart.length === 0) {
        alert('Keranjang belanja Anda kosong!');
        return;
    }

    // Data pemesan
    const userName = "Nama Pemesan"; // Ganti dengan nama yang relevan jika tersedia
    const phoneNumber = "+6281585813432"; // Nomor WhatsApp tujuan

    // Membentuk pesan berdasarkan keranjang
    let message = `Halo, saya ingin memesan galon:\nNama: \nAlamat Pengiriman:\n\n`;
    let totalPrice = 0;

    cart.forEach((item) => {
        message += `${item.name} - ${item.quantity} x Rp${item.price.toLocaleString()} = Rp${(item.price * item.quantity).toLocaleString()}\n`;
        totalPrice += item.price * item.quantity;
    });

    message += `\nTotal Harga: Rp${totalPrice.toLocaleString()}\n\nSilakan proses pesanan saya.`;

    // Encode pesan dan buat link WhatsApp
    const encodedMessage = encodeURIComponent(message);
    const whatsappLink = `https://wa.me/${phoneNumber}?text=${encodedMessage}`;

    // Redirect ke WhatsApp
    window.open(whatsappLink, "_blank");
}

// Profile Dropdown Functions
function toggleProfileMenu() {
    document.getElementById("profileDropdown").classList.toggle("show");
}

// Close dropdown when clicking outside
window.onclick = function(event) {
    if (!event.target.matches('.profile-btn')) {
        const dropdowns = document.getElementsByClassName("dropdown-content");
        for (let i = 0; i < dropdowns.length; i++) {
            const openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

// Notification System
function showNotification(message, type = 'success') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    // Add notification to document
    document.body.appendChild(notification);
    
    // Show notification
    setTimeout(() => {
        notification.classList.add('show');
    }, 100);
    
    // Remove notification after 3 seconds
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => {
            document.body.removeChild(notification);
        }, 300);
    }, 3000);
}

// Initialize cart from localStorage if available
document.addEventListener('DOMContentLoaded', () => {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
        cart = JSON.parse(savedCart);
        updateCartCount();
    }
});

// Save cart to localStorage when modified
function saveCart() {
    localStorage.setItem('cart', JSON.stringify(cart));
}

// Update cart functions to include saving
const originalAddToCart = addToCart;
addToCart = (...args) => {
    originalAddToCart(...args);
    saveCart();
};

const originalRemoveFromCart = removeFromCart;
removeFromCart = (...args) => {
    originalRemoveFromCart(...args);
    saveCart();
};

const originalUpdateQuantity = updateQuantity;
updateQuantity = (...args) => {
    originalUpdateQuantity(...args);
    saveCart();
};

// Close cart when pressing Escape key
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        const cartPopup = document.querySelector('.cart-popup');
        if (cartPopup.style.display === 'block') {
            toggleCart();
        }
    }
});

// Prevent closing cart when clicking inside it
document.querySelector('.cart-popup').addEventListener('click', (e) => {
    e.stopPropagation();
});
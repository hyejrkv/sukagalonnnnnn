/*
// Constants for API endpoints
const API_ENDPOINTS = {
    PRODUCTS: '/api/products'
};

// Fetch products from database
async function fetchProducts() {
    try {
        const response = await fetch(API_ENDPOINTS.PRODUCTS);
        const products = await response.json();
        populateProductTable(products);
    } catch (error) {
        console.error('Error fetching products:', error);
        showError('Failed to load products');
    }
}

// Add new product
async function addProduct(productData) {
    try {
        const response = await fetch(API_ENDPOINTS.PRODUCTS, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });
        if (!response.ok) throw new Error('Failed to add product');
        fetchProducts(); // Refresh the product list
        showSuccess('Product added successfully!');
    } catch (error) {
        console.error('Error adding product:', error);
        showError('Failed to add product');
    }
}

// Update existing product
async function updateProduct(productId, productData) {
    try {
        const response = await fetch(`${API_ENDPOINTS.PRODUCTS}/${productId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });
        if (!response.ok) throw new Error('Failed to update product');
        fetchProducts(); // Refresh the product list
        showSuccess('Product updated successfully!');
    } catch (error) {
        console.error('Error updating product:', error);
        showError('Failed to update product');
    }
}

// Delete product
async function deleteProduct(productId) {
    try {
        const response = await fetch(`${API_ENDPOINTS.PRODUCTS}/${productId}`, {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Failed to delete product');
        fetchProducts(); // Refresh the product list
        showSuccess('Product deleted successfully!');
    } catch (error) {
        console.error('Error deleting product:', error);
        showError('Failed to delete product');
    }
}
*/
// UI Functions
function populateProductTable(products) {
    const tbody = document.getElementById('productTableBody');
    tbody.innerHTML = products.map(product => `
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>Rp ${product.price.toLocaleString()}</td>
            <td>${product.stock}</td>
            <td>
                <button onclick="editProduct(${product.id})">Edit</button>
                <button onclick="confirmDelete(${product.id})">Hapus</button>
            </td>
        </tr>
    `).join('');
}

function showAddProductModal() {
    document.getElementById('modalTitle').textContent = 'Tambah Produk Baru';
    document.getElementById('productForm').reset();
    document.getElementById('productModal').style.display = 'block';
}

function editProduct(productId) {
    // Fetch product details and populate form
    fetch(`${API_ENDPOINTS.PRODUCTS}/${productId}`)
        .then(response => response.json())
        .then(product => {
            document.getElementById('productName').value = product.name;
            document.getElementById('productPrice').value = product.price;
            document.getElementById('productStock').value = product.stock;
            document.getElementById('productForm').dataset.productId = productId;
            document.getElementById('productModal').style.display = 'block';
        })
        .catch(error => showError('Failed to load product details'));
}

function confirmDelete(productId) {
    if (confirm('Yakin ingin menghapus produk ini?')) {
        deleteProduct(productId);
    }
}

function closeProductModal() {
    document.getElementById('productModal').style.display = 'none';
    document.getElementById('productForm').reset();
    delete document.getElementById('productForm').dataset.productId;
}

function showError(message) {
    alert(message);
}

function showSuccess(message) {
    alert(message);
}

// Form submission handler
document.getElementById('productForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const formData = {
        name: document.getElementById('productName').value,
        price: parseInt(document.getElementById('productPrice').value),
        stock: parseInt(document.getElementById('productStock').value)
    };

    const productId = this.dataset.productId;
    if (productId) {
        await updateProduct(productId, formData);
    } else {
        await addProduct(formData);
    }

    closeProductModal();
});

// Initialize
document.addEventListener('DOMContentLoaded', fetchProducts);

// Profile menu
function toggleProfileMenu() {
    const dropdown = document.getElementById('profileDropdown');
    dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
}

function logout() {
    window.location.href = 'SG_LOGIN-ADMIN.html';
}
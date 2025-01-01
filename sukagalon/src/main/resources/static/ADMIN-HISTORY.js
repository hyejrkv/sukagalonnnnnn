/*// Constants for API endpoints
const API_ENDPOINTS = {
    USERS: '/api/users',
    ORDERS: '/api/orders'
};

// Fetch users from database
async function fetchUsers() {
    try {
        const response = await fetch(API_ENDPOINTS.USERS);
        const users = await response.json();
        populateUserTable(users);
    } catch (error) {
        console.error('Error fetching users:', error);
        showError('Failed to load users');
    }
}

// Fetch orders for specific user
async function fetchUserOrders(userId) {
    try {
        const response = await fetch(`${API_ENDPOINTS.ORDERS}?userId=${userId}`);
        const orders = await response.json();
        populateOrderTable(orders);
    } catch (error) {
        console.error('Error fetching orders:', error);
        showError('Failed to load orders');
    }
}

// Add new order
async function addOrder(orderData) {
    try {
        const response = await fetch(API_ENDPOINTS.ORDERS, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        });
        if (!response.ok) throw new Error('Failed to add order');
        fetchUserOrders(selectedUserId);
        showSuccess('Order added successfully!');
    } catch (error) {
        console.error('Error adding order:', error);
        showError('Failed to add order');
    }
}

// Update existing order
async function updateOrder(orderId, orderData) {
    try {
        const response = await fetch(`${API_ENDPOINTS.ORDERS}/${orderId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        });
        if (!response.ok) throw new Error('Failed to update order');
        if (selectedUserId) {
            await fetchUserOrders(selectedUserId); // Refresh user orders
        }
        showSuccess('Order updated successfully!');
    } catch (error) {
        console.error('Error updating order:', error);
        showError('Failed to update order');
    }
}

// Delete order
async function deleteOrder(orderId) {
    try {
        const response = await fetch(`${API_ENDPOINTS.ORDERS}/${orderId}`, {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Failed to delete order');
        fetchUserOrders(selectedUserId);
        showSuccess('Order deleted successfully!');
    } catch (error) {
        console.error('Error deleting order:', error);
        showError('Failed to delete order');
    }
}
*/
// UI Functions
let selectedUserId = null;

function populateUserTable(users) {
    const tbody = document.getElementById('userTableBody');
    tbody.innerHTML = users.map(user => `
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>
                <button onclick="selectUser(${user.id})">Lihat Riwayat</button>
            </td>
        </tr>
    `).join('');
}

function populateOrderTable(orders) {
    const tbody = document.getElementById('orderTableBody');
    tbody.innerHTML = orders.map(order => `
        <tr>
            <td>${order.id}</td>
            <td>${order.date}</td>
            <td>${order.product}</td>
            <td>${order.quantity}</td>
            <td>Rp ${order.total.toLocaleString()}</td>
            <td>${order.status}</td>
            <td>
                <button onclick="editOrder(${order.id})">Edit</button>
                <button onclick="confirmDelete(${order.id})">Hapus</button>
            </td>
        </tr>
    `).join('');
}

function selectUser(userId) {
    selectedUserId = userId;
    fetchUserOrders(userId);
}

function showAddOrderModal() {
    if (!selectedUserId) {
        showError('Pilih pembeli terlebih dahulu');
        return;
    }
    document.getElementById('modalTitle').textContent = 'Tambah Pesanan Baru';
    document.getElementById('orderForm').reset();
    document.getElementById('orderModal').style.display = 'block';
}

function editOrder(orderId) {
    fetch(`${API_ENDPOINTS.ORDERS}/${orderId}`)
        .then(response => response.json())
        .then(order => {
            document.getElementById('orderDate').value = order.date;
            document.getElementById('product').value = order.product;
            document.getElementById('quantity').value = order.quantity;
            document.getElementById('status').value = order.status;
            document.getElementById('orderForm').dataset.orderId = orderId;
            document.getElementById('orderModal').style.display = 'block';
        })
        .catch(error => showError('Failed to load order details'));
}

function confirmDelete(orderId) {
    if (confirm('Yakin ingin menghapus pesanan ini?')) {
        deleteOrder(orderId);
    }
}

function closeOrderModal() {
    document.getElementById('orderModal').style.display = 'none';
    document.getElementById('orderForm').reset();
    delete document.getElementById('orderForm').dataset.orderId;
}

function showError(message) {
    alert(message);
}

function showSuccess(message) {
    alert(message);
}

// Form submission handler
document.getElementById('orderForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    const formData = {
        userId: selectedUserId,
        date: document.getElementById('orderDate').value,
        product: document.getElementById('product').value,
        quantity: parseInt(document.getElementById('quantity').value),
        status: document.getElementById('status').value,
        total: parseInt(document.getElementById('quantity').value) * 15000
    };
    const orderId = this.dataset.orderId;
    if (orderId) {
        await updateOrder(orderId, formData);
    } else {
        await addOrder(formData);
    }
    closeOrderModal();
});

// Initialize
document.addEventListener('DOMContentLoaded', fetchUsers);

// Profile menu
function toggleProfileMenu() {
    const dropdown = document.getElementById('profileDropdown');
    dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
}

function logout() {
    window.location.href = 'ADMIN-LOGIN.html';
} 
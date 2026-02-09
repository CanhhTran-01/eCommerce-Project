
export function renderStatusBadgeHTML(status) {
    switch (status) {
        case "PENDING":
            return `<span class="badge bg-secondary">Chờ xử lý</span>`;
        case "CONFIRMED":
            return `<span class="badge bg-info">Đã xác nhận</span>`;
        case "SHIPPING":
            return `<span class="badge bg-primary">Đang giao</span>`;
        case "COMPLETED":
            return `<span class="badge bg-success">Đã giao</span>`;
        case "CANCELED":
            return `<span class="badge bg-danger">Đã hủy</span>`;
        default:
            return `<span class="badge bg-warning text-dark">unknown</span>`;
    }
}
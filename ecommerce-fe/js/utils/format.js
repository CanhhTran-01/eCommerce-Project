
export function formatVND(value) {
    if (value == null || isNaN(value)) return '0₫';

    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(value);
}

export function formatDateVN(dateString) {
    if (!dateString) return "...";

    const date = new Date(dateString);

    if (isNaN(date.getTime())) return dateString;

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
};

export function formatDateTime(isoDate) {
    const date = new Date(isoDate);
    return date.toLocaleDateString('vi-VN', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

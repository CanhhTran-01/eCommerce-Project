
export function formatVND(value) {
    if (value == null || isNaN(value)) return '0₫';

    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(value);
}

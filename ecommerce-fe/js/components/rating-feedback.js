
export function getRatingFromCard(container) {
    const rating = Number(container.dataset.rating);
    return rating || 0;
}

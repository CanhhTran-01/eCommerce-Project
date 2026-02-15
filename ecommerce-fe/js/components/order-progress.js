
export function renderOrderProgress(container, status) {

    const STATUS_STEPS = {
        PENDING: 0,
        CONFIRMED: 1,
        SHIPPING: 2,
        COMPLETED: 3
    };

    const steps = [
        { label: "Đã đặt", icon: "bi-check" },
        { label: "Xác nhận", icon: "bi-check" },
        { label: "Đang giao", icon: "bi-truck" },
        { label: "Hoàn thành", icon: "bi-box" }
    ];

    const currentStep = STATUS_STEPS[status];

    if (currentStep === undefined) {
        console.error("Invalid order status:", status);
        return;
    }

    const progressPercent = (currentStep / (steps.length - 1)) * 100;
    
    container.innerHTML = `
        <div class="position-absolute top-50 start-0 w-100 translate-middle-y"
            style="height:4px; background-color:#dee2e6;"></div>

        <div class="position-absolute top-50 start-0 translate-middle-y bg-warning"
            style="height:4px; width:${progressPercent}%;"></div>

        <div class="d-flex justify-content-between position-relative">
            ${steps.map((step, index) => {

        let circleClass = "bg-secondary text-white";
        let textClass = "text-muted";
        let icon = "bi-circle";

        if (index < currentStep) {
            circleClass = "bg-success text-white";
            textClass = "text-success fw-semibold";
            icon = "bi-check";
        }
        else if (index === currentStep) {
            circleClass = "bg-warning text-dark";
            textClass = "text-warning fw-semibold";
            icon = step.icon;
        }

        return `
                    <div class="text-center">
                        <div class="rounded-circle ${circleClass} d-flex align-items-center justify-content-center mx-auto"
                            style="width:40px;height:40px;">
                            <i class="bi ${icon}"></i>
                        </div>
                        <div class="small mt-2 ${textClass}">
                            ${step.label}
                        </div>
                    </div>
                `;
    }).join("")}
        </div>
    `;
}

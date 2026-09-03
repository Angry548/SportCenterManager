document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "paymentMethodSearch"
        );

    const clearButton =
        document.getElementById(
            "paymentMethodSearchClear"
        );

    const rows =
        document.querySelectorAll(
            "[data-payment-method-row]"
        );

    const visibleCount =
        document.getElementById(
            "paymentMethodVisibleCount"
        );

    const noResults =
        document.getElementById(
            "paymentMethodNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterPaymentMethods = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        rows.forEach(row => {

            const name =
                normalizeText(
                    row.dataset.paymentMethodName
                );

            const status =
                normalizeText(
                    row.dataset.paymentMethodStatus
                );


            const matches =
                name.includes(query) ||
                status.includes(query);


            row.hidden =
                !matches;


            if (matches) {
                visible++;
            }

        });


        if (visibleCount) {

            visibleCount.textContent =
                visible;

        }


        if (clearButton) {

            clearButton.classList.toggle(
                "visible",
                query.length > 0
            );

        }


        if (noResults) {

            noResults.hidden =
                visible !== 0 ||
                query.length === 0;

        }

    };


    searchInput?.addEventListener(
        "input",
        filterPaymentMethods
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterPaymentMethods();

            searchInput.focus();

        }
    );


    /* =====================================================
       FORM PREVIEW
       ===================================================== */

    const nameInput =
        document.querySelector(
            "[data-payment-method-name]"
        );

    const characterCount =
        document.getElementById(
            "paymentMethodCharacterCount"
        );

    const namePreview =
        document.getElementById(
            "paymentMethodNamePreview"
        );


    const updatePreview = () => {

        if (!nameInput) {
            return;
        }


        const value =
            nameInput.value.trim();


        if (characterCount) {

            characterCount.textContent =
                nameInput.value.length;

        }


        if (namePreview) {

            namePreview.textContent =
                value || "Nombre del método";

        }

    };


    nameInput?.addEventListener(
        "input",
        updatePreview
    );


    updatePreview();


    /* =====================================================
       ACTIVAR / DESACTIVAR
       ===================================================== */

    const stateLinks =
        document.querySelectorAll(
            "[data-payment-method-state]"
        );


    stateLinks.forEach(link => {

        link.addEventListener(
            "click",
            event => {

                event.preventDefault();


                const action =
                    link.dataset.paymentMethodState;

                const activating =
                    action === "activate";


                const url =
                    link.getAttribute("href");


                if (!url) {
                    return;
                }


                const title =
                    activating
                        ? "Activar método de pago"
                        : "Desactivar método de pago";


                const message =
                    activating
                        ? "El método volverá a quedar disponible."
                        : "El método quedará marcado como inactivo.";


                if (typeof Swal === "undefined") {

                    if (window.confirm(message)) {

                        window.location.href =
                            url;

                    }

                    return;

                }


                Swal.fire({

                    title: title,

                    text: message,

                    icon:
                        activating
                            ? "question"
                            : "warning",

                    showCancelButton: true,

                    confirmButtonText:
                        activating
                            ? "Sí, activar"
                            : "Sí, desactivar",

                    cancelButtonText:
                        "Cancelar",

                    reverseButtons: true

                }).then(result => {

                    if (result.isConfirmed) {

                        window.location.href =
                            url;

                    }

                });

            }
        );

    });

});
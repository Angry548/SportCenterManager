document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "membershipTypeSearch"
        );

    const clearButton =
        document.getElementById(
            "membershipTypeSearchClear"
        );

    const rows =
        document.querySelectorAll(
            "[data-membership-type-row]"
        );

    const visibleCount =
        document.getElementById(
            "membershipTypeVisibleCount"
        );

    const noResults =
        document.getElementById(
            "membershipTypeNoSearchResults"
        );


    const normalizeText = value =>
        String(value ?? "")
            .toLowerCase()
            .trim();


    const filterMembershipTypes = () => {

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
                    row.dataset.membershipTypeName
                );

            const price =
                normalizeText(
                    row.dataset.membershipTypePrice
                );

            const duration =
                normalizeText(
                    row.dataset.membershipTypeDuration
                );

            const status =
                normalizeText(
                    row.dataset.membershipTypeStatus
                );


            const matches =
                name.includes(query) ||
                price.includes(query) ||
                duration.includes(query) ||
                status.includes(query);


            row.hidden =
                !matches;


            if (matches) {
                visible++;
            }

        });


        if (visibleCount) {
            visibleCount.textContent = visible;
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
        filterMembershipTypes
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterMembershipTypes();

            searchInput.focus();

        }
    );


    /* =====================================================
       FORM PREVIEW
       ===================================================== */

    const nameInput =
        document.querySelector(
            "[data-membership-name]"
        );

    const priceInput =
        document.querySelector(
            "[data-membership-price]"
        );

    const durationInput =
        document.querySelector(
            "[data-membership-duration]"
        );


    const namePreview =
        document.getElementById(
            "membershipTypeNamePreview"
        );

    const pricePreview =
        document.getElementById(
            "membershipTypePricePreview"
        );

    const durationPreview =
        document.getElementById(
            "membershipTypeDurationPreview"
        );

    const nameCount =
        document.getElementById(
            "membershipTypeNameCount"
        );


    const updatePreview = () => {

        if (nameInput) {

            const name =
                nameInput.value.trim();


            if (namePreview) {

                namePreview.textContent =
                    name || "Nombre del plan";

            }


            if (nameCount) {

                nameCount.textContent =
                    nameInput.value.length;

            }

        }


        if (
            priceInput &&
            pricePreview
        ) {

            const price =
                priceInput.value.trim();


            pricePreview.textContent =
                price || "—";

        }


        if (
            durationInput &&
            durationPreview
        ) {

            const duration =
                parseInt(
                    durationInput.value,
                    10
                );


            if (
                Number.isNaN(duration) ||
                duration < 1
            ) {

                durationPreview.textContent =
                    "—";

            } else {

                durationPreview.textContent =
                    `${duration} ${duration === 1 ? "día" : "días"}`;

            }

        }

    };


    nameInput?.addEventListener(
        "input",
        updatePreview
    );

    priceInput?.addEventListener(
        "input",
        updatePreview
    );

    durationInput?.addEventListener(
        "input",
        updatePreview
    );


    updatePreview();


    /* =====================================================
       ACTIVAR / DESACTIVAR
       ===================================================== */

    const statusForms =
        document.querySelectorAll(
            ".membership-type-status-form"
        );


    statusForms.forEach(form => {

        form.addEventListener(
            "submit",
            event => {

                event.preventDefault();


                const action =
                    form.dataset.membershipStatus;

                const activating =
                    action === "activate";


                const title =
                    activating
                        ? "Activar tipo de membresía"
                        : "Desactivar tipo de membresía";


                const message =
                    activating
                        ? "El tipo de membresía volverá a quedar activo."
                        : "El tipo quedará marcado como inactivo.";


                if (typeof Swal === "undefined") {

                    if (window.confirm(message)) {
                        form.submit();
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
                        form.submit();
                    }

                });

            }
        );

    });

});
document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById("userSearch");

    const clearButton =
        document.getElementById("userSearchClear");

    const userRows =
        document.querySelectorAll("[data-user-row]");

    const visibleCount =
        document.getElementById("userVisibleCount");

    const noResults =
        document.getElementById("userNoSearchResults");


    const normalizeText = value =>
        String(value ?? "")
            .toLowerCase()
            .trim();


    const filterUsers = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(searchInput.value);

        let visible = 0;


        userRows.forEach(row => {

            const email =
                normalizeText(
                    row.dataset.userEmail
                );

            const role =
                normalizeText(
                    row.dataset.userRole
                );


            const matches =
                email.includes(query) ||
                role.includes(query);


            row.hidden = !matches;


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
        filterUsers
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }

            searchInput.value = "";

            filterUsers();

            searchInput.focus();

        }
    );


    /* =====================================================
       MOSTRAR / OCULTAR PASSWORD
       ===================================================== */

    const passwordToggles =
        document.querySelectorAll(
            "[data-password-toggle]"
        );


    passwordToggles.forEach(button => {

        button.addEventListener(
            "click",
            () => {

                const inputId =
                    button.dataset.passwordToggle;

                const input =
                    document.getElementById(inputId);


                if (!input) {
                    return;
                }


                const showing =
                    input.type === "text";


                input.type =
                    showing
                        ? "password"
                        : "text";


                const icon =
                    button.querySelector("i");


                if (icon) {

                    icon.className =
                        showing
                            ? "bi bi-eye"
                            : "bi bi-eye-slash";

                }

            }
        );

    });


    /* =====================================================
       ACTIVAR / DESACTIVAR
       ===================================================== */

    const statusForms =
        document.querySelectorAll(
            ".user-status-form"
        );


    statusForms.forEach(form => {

        form.addEventListener(
            "submit",
            event => {

                event.preventDefault();


                const action =
                    form.dataset.userStatusAction;


                const activating =
                    action === "activate";


                const title =
                    activating
                        ? "Activar usuario"
                        : "Desactivar usuario";


                const message =
                    activating
                        ? "La cuenta volverá a quedar habilitada."
                        : "La cuenta quedará inactiva hasta que sea habilitada nuevamente.";


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


    /* =====================================================
       CAMBIO DE CONTRASEÑA
       ===================================================== */

    const passwordForm =
        document.getElementById(
            "changePasswordForm"
        );

    const newPassword =
        document.getElementById(
            "newPassword"
        );

    const confirmPassword =
        document.getElementById(
            "confirmPassword"
        );

    const passwordMatchError =
        document.getElementById(
            "passwordMatchError"
        );


    passwordForm?.addEventListener(
        "submit",
        event => {

            if (
                !newPassword ||
                !confirmPassword
            ) {
                return;
            }


            const matches =
                newPassword.value ===
                confirmPassword.value;


            if (!matches) {

                event.preventDefault();

                if (passwordMatchError) {
                    passwordMatchError.hidden = false;
                }

                confirmPassword.focus();

                return;
            }


            if (passwordMatchError) {
                passwordMatchError.hidden = true;
            }

        }
    );


    confirmPassword?.addEventListener(
        "input",
        () => {

            if (passwordMatchError) {
                passwordMatchError.hidden = true;
            }

        }
    );

});
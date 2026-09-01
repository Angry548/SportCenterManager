document.addEventListener("DOMContentLoaded", () => {

    const sidebar =
        document.getElementById("appSidebar");

    const sidebarOpen =
        document.getElementById("sidebarOpen");

    const sidebarClose =
        document.getElementById("sidebarClose");

    const sidebarOverlay =
        document.getElementById("sidebarOverlay");


    /* =========================
       SIDEBAR
       ========================= */

    const openSidebar = () => {

        if (!sidebar || !sidebarOverlay) {
            return;
        }

        sidebar.classList.add("open");
        sidebarOverlay.classList.add("show");

    };


    const closeSidebar = () => {

        if (!sidebar || !sidebarOverlay) {
            return;
        }

        sidebar.classList.remove("open");
        sidebarOverlay.classList.remove("show");

    };


    sidebarOpen?.addEventListener(
        "click",
        openSidebar
    );


    sidebarClose?.addEventListener(
        "click",
        closeSidebar
    );


    sidebarOverlay?.addEventListener(
        "click",
        closeSidebar
    );


    /* =========================
       OPCIÓN ACTIVA
       ========================= */

    const currentPath =
        window.location.pathname;

    const sidebarLinks =
        document.querySelectorAll(
            ".sidebar-link"
        );


    sidebarLinks.forEach(link => {

        const href =
            link.getAttribute("href");

        if (!href || href === "#") {
            return;
        }


        const isActive =
            currentPath === href ||
            currentPath.startsWith(
                href + "/"
            );


        if (isActive) {

            link.classList.add(
                "active"
            );

        }

    });


    /* =========================
       RESPONSIVE
       ========================= */

    window.addEventListener(
        "resize",
        () => {

            if (
                window.innerWidth >= 992
            ) {

                closeSidebar();

            }

        }
    );

});
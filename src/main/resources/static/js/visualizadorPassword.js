const passwordInput = document.querySelector('input[type="password"]');
        const showPasswordButton = document.getElementById('showPassword');
        const eyeIcon = document.getElementById('eyeIcon');

        let passwordVisible = false;

        showPasswordButton.addEventListener('click', () => {
            if (passwordVisible) {
                passwordInput.type = 'password';
                eyeIcon.classList.remove('fa-eye-slash');
                eyeIcon.classList.add('fa-eye');
            } else {
                passwordInput.type = 'text';
                eyeIcon.classList.remove('fa-eye');
                eyeIcon.classList.add('fa-eye-slash');
            }

            passwordVisible = !passwordVisible;
        });
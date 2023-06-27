function onGoogleSignIn(googleUser) {
            // Get the user's ID token
            var idToken = googleUser.getAuthResponse().id_token;

            // Send the ID token to the server for verification and authentication
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/loginWithGoogle');
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function() {
                // Handle the response from the server
                if (xhr.status === 200) {
                    // Successful authentication
                    window.location.href = '/dashboard';
                } else {
                    // Failed authentication
                    console.error('Google Sign-In failed.');
                }
            };
            xhr.send('idToken=' + idToken);
        }
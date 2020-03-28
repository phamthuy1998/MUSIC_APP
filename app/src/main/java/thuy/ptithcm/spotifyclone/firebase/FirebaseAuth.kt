package thuy.ptithcm.spotifyclone.firebase

import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import thuy.ptithcm.spotifyclone.data.User
import thuy.ptithcm.spotifyclone.utils.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FirebaseAuth {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseDatabase: FirebaseDatabase? by lazy {
        FirebaseDatabase.getInstance()
    }

    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful) {
                        // Check email Verified
                        if (currentUser()?.isEmailVerified == true) {
                            // Set email active
                            databaseRef()?.child(USER)?.child(currentUser()?.uid.toString())
                                ?.child("active")?.setValue(true)
                            emitter.onComplete()
                        } else
                            emitter.onError(Throwable(ERR_EMAIL_VERIFY))
                    } else
                        try {
                            throw it.exception!!
                        } catch (emailNotExist: FirebaseAuthInvalidUserException) {
                            emitter.onError(Throwable(ERR_EMAIL_NOT_Exist))
                        } catch (password: FirebaseAuthInvalidCredentialsException) {
                            emitter.onError(Throwable(ERR_INCORRECT_PW))
                        } catch (error: Exception) {
                            emitter.onError(Throwable(it.exception))
                        }
                }else emitter.onError(Throwable(it.exception))
            }
    }


    fun sendMailResetPassword(email: String) = Completable.create { emitter ->
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    try {
                        throw it.exception!!
                    } catch (emailNotExist: FirebaseAuthInvalidUserException) {
                        emitter.onError(Throwable(ERR_EMAIL_NOT_Exist))
                    } catch (error: Exception) {
                        emitter.onError(Throwable(it.exception))
                    }
            }else emitter.onError(Throwable(it.exception))
        }
    }

    fun register(
        email: String,
        username: String,
        dayOfBirth: String,
        gender: Boolean,
        password: String
    ) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    emitter.onComplete()
                    verifyEmail()
                    addUserDatabase(email, username, dayOfBirth, gender, password)
                } else
                    try {
                        throw it.exception!!
                    } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                        emitter.onError(Throwable(ERR_WEAK_PASSWORD))
                    } catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                        emitter.onError(Throwable(ERR_EMAIL_INVALID))
                    } catch (existEmail: FirebaseAuthUserCollisionException) {
                        emitter.onError(Throwable(ERR_EMAIL_EXIST))
                    } catch (error: Exception) {
                        emitter.onError(Throwable(it.exception))
                    }
            }else emitter.onError(Throwable(it.exception))
        }
    }

    private fun verifyEmail() {
        currentUser()?.sendEmailVerification()
    }

    private fun addUserDatabase(
        _email: String,
        _username: String,
        _dayOfBirth: String,
        _gender: Boolean,
        _password: String
    ) {
        val uerUID = currentUser()?.uid
        val user = User().apply {
            id = uerUID
            email = _email
            username = _username
            dayOfBirth = _dayOfBirth
            gender = _gender
            password = _password
            active = false
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val dayCreate = current.format(formatter)
            dayCreateAcc = dayCreate
        }
        databaseRef()?.child(USER)?.child(uerUID.toString())?.setValue(user)
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    private fun databaseRef() = firebaseDatabase?.reference

}
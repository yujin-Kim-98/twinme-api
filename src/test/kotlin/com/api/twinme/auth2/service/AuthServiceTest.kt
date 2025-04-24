//package com.api.twinme.auth2.service
//
//import com.api.twinme.auth2.repository.FakeUserRepository
//import com.api.twinme.config.security.jwt.JwtUserDetailsService
//import com.api.twinme.exception.ExistUserException
//import com.api.twinme.exception.NotFoundUserException
//import io.kotest.assertions.throwables.shouldThrow
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.shouldBe
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.security.crypto.password.PasswordEncoder
//
//@SpringBootTest
//class AuthServiceTest(
//    @Value("\${jwt.secret}") private val secretKey: String,
//    @Value("\${jwt.token-expiration}") private val tokenExpiration: Long,
//    @Value("\${jwt.refresh-token-expiration}") private val refreshTokenExpiration: Long,
//    private val passwordEncoder: PasswordEncoder,
//): BehaviorSpec({
//
//    lateinit var authService: AuthService
//
//    beforeTest {
//
//        val fakeUserRepository = FakeUserRepository()
//        val userDetailsService = JwtUserDetailsService(fakeUserRepository)
//        val jwtTokenUtils = JwtTokenUtils(secretKey, tokenExpiration, refreshTokenExpiration)
//        val userPersistService = UserPersistService(fakeUserRepository)
//
//        authService = AuthService(
//            passwordEncoder,
//            userPersistService,
//            fakeUserRepository,
//            userDetailsService,
//            jwtTokenUtils
//        )
//
//        fakeUserRepository.save(
//            User(
//                provider = Provider.GOOGLE,
//                encryptedSub = passwordEncoder.encode("1234"),
//                hashedSub = authService.hashWithSHA256("1234"),
//                email = "yujin123.kim@gmail.com",
//                nickname = "유찌",
//                age = 28
//            )
//        )
//    }
//
//    Given("existUser를 이용해서 이미 존재하는 유저인지 확인할 수 있다") {
//        val provider = Provider.GOOGLE.name
//
//        When("이미 존재하는 유저일 때") {
//            val sub = "1234"
//            val isExist = authService.existUser(sub, provider)
//
//            Then("true가 리턴된다.") {
//                isExist shouldBe true
//            }
//
//        }
//
//        When("이미 존재하지 않는 유저일 때") {
//            val sub = "12345"
//            val isExist = authService.existUser(sub, provider)
//
//            Then("false가 리턴된다.") {
//                isExist shouldBe false
//            }
//
//        }
//
//    }
//
//    Given("회원가입") {
//
//        When("이미 존재하는 회원이면") {
//            val request = SignUpRequest(
//                sub = "1234",
//                provider = Provider.GOOGLE.name,
//                email = "yujin123.kim@gmail.com",
//                nickname = "유찌",
//                age = 28
//            )
//
//            Then("ExistUserException을 throw 한다.") {
//                shouldThrow<ExistUserException> {
//                    authService.signUp(request)
//                }
//            }
//        }
//
//        When("존재하지 않는 회원이면") {
//            val request = SignUpRequest(
//                sub = "12345",
//                provider = Provider.GOOGLE.name,
//                email = "kyj981128@naver.com",
//                nickname = "감자",
//                age = 20
//            )
//
//            val response: SignInResponse = authService.signUp(request)
//
//            Then("회원가입 후 JWT 토큰을 발급한다.") {
//                response.userInfo.email shouldBe request.email
//                response.userInfo.nickname shouldBe request.nickname
//            }
//        }
//
//    }
//
//    Given("로그인") {
//        When("존재하지 않는 회원이면") {
//            val request = SignInRequest(
//                sub = "123465",
//                provider = Provider.GOOGLE.name
//            )
//            Then("NotFoundUserException을 throw한다.") {
//                shouldThrow<NotFoundUserException> {
//                    authService.signIn(request)
//                }
//            }
//        }
//
//        When("존재하는 회원이면") {
//            val request = SignInRequest(
//                sub = "1234",
//                provider = Provider.GOOGLE.name
//            )
//
//            val response = authService.signIn(request)
//            Then("로그인 정보와 JWT 토큰을 발급한다.") {
//                response.userInfo.id shouldBe 1L
//                response.userInfo.nickname shouldBe "유찌"
//            }
//        }
//    }
//
//})
package com.example.taskmanager.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.exceptions.hash.HashException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashService {

  private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
  private static final int ITERATIONS = 65536;
  private static final int KEY_LENGTH = 128;

  /*
   * Aplica o algoritmo de hash na senha.
   */
  public static String apply(String password, byte[] salt) throws HashException {
    try {
      PBEKeySpec spec = createKeySpec(password, salt);
      var factory = getSecretKeyFactory();
      byte[] hash = generateHash(factory, spec);
      return encodeBase64(hash);
    } catch (Exception e) {
      var message = "Ocorreu algum erro interno durante o processamento de criptografia da senha";
      throw new HashException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * Compara a senha com o hash.
   */
  public static boolean compare(String password, String hash, byte[] salt) throws HashException {
    try {
      byte[] decodedHash = decodeBase64(hash);
      PBEKeySpec spec = createKeySpec(password, salt);
      var factory = getSecretKeyFactory();
      byte[] newHash = generateHash(factory, spec);
      return MessageDigest.isEqual(decodedHash, newHash);
    } catch (Exception e) {
      var message = "Ocorreu algum erro interno durante o processamento de comparação da senha";
      throw new HashException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Gera um salt aleatório.
   */
  public static byte[] generateSalt() {
    var random = new SecureRandom();
    var salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  /**
   * Cria uma especificação de chave baseada em senha para a geração de uma chave
   * secreta.
   */
  private static PBEKeySpec createKeySpec(String password, byte[] salt) {
    return new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
  }

  /**
   * Retorna uma instância de SecretKeyFactory com base no algoritmo de hash.
   */
  private static SecretKeyFactory getSecretKeyFactory() throws NoSuchAlgorithmException {
    return SecretKeyFactory.getInstance(ALGORITHM);
  }

  /**
   * Gera a chave secreta com base na especificação de chave.
   */
  private static byte[] generateHash(SecretKeyFactory factory, PBEKeySpec spec) throws InvalidKeySpecException {
    return factory.generateSecret(spec).getEncoded();
  }

  /**
   * Codifica o hash da senha em base64.
   */
  private static String encodeBase64(byte[] hash) {
    return Base64.getEncoder().encodeToString(hash);
  }

  /*
   * Decodifica o hash da senha em base64.
   */
  private static byte[] decodeBase64(String hash) {
    return Base64.getDecoder().decode(hash);
  }
}

package com.udea.lab12025v.services;


import com.udea.lab12025v.DTO.TransactionDTO;
import com.udea.lab12025v.entity.Customer;
import com.udea.lab12025v.entity.Transaction;
import com.udea.lab12025v.repository.CustomerRepository;
import com.udea.lab12025v.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;//Para validar cuentas

    public TransactionDTO transferMoney(TransactionDTO transactionDTO) {
        //validar que los numeros de cuenta no sean nulos
        if(transactionDTO.getSenderAccountNumber()==null || transactionDTO.getReceiverAccountNumber()==null){
            throw new IllegalArgumentException("Los números de cuenta del remitente y receptor son obligatorios");
        }

        //Buscar los clientes por numero de cuenta
        Customer sender = customerRepository.findByAccountNumber(transactionDTO.getSenderAccountNumber())
                .orElseThrow(()-> new IllegalArgumentException("La cuenta del remitente no existe"));

        Customer receiver = customerRepository.findByAccountNumber(transactionDTO.getReceiverAccountNumber())
                .orElseThrow(()-> new IllegalArgumentException("La cuenta del receptor no existe"));

        //Validar que el remitente tenga saldo suficiente
        if(sender.getBalance() < transactionDTO.getAmount()){
            throw new IllegalArgumentException("saldo insuficiente en la cuenta del remitente");
        }

        //realiza la transferencia
        sender.setBalance(sender.getBalance() - transactionDTO.getAmount());
        receiver.setBalance(receiver.getBalance() + transactionDTO.getAmount());

        //Guardar los cambios en las cuentas
        customerRepository.save(sender);
        customerRepository.save(receiver);

        //Crear y guardar la transaccion
        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(sender.getAccountNumber());
        transaction.setReceiverAccountNumber(receiver.getAccountNumber());
        transaction.setAmount(transactionDTO.getAmount());
        transaction= transactionRepository.save(transaction);

        //Devolver la transaccion creada como un DTO
        TransactionDTO  savedTransaction = new TransactionDTO();
        savedTransaction.setId(transaction.getId());
        savedTransaction.setSenderAccountNumber(transaction.getSenderAccountNumber());
        savedTransaction.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
        savedTransaction.setAmount(transaction.getAmount());
        return savedTransaction;

    }

    public List<TransactionDTO> getTransactionsForAccount(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(accountNumber,accountNumber);
        return transactions.stream().map(transaction -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setId(transaction.getId());
            dto.setSenderAccountNumber(transaction.getSenderAccountNumber());
            dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
            dto.setAmount(transaction.getAmount());
            return dto;
        }).collect(Collectors.toList());
    }

}
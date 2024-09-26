package sg.com.petpal.petpal;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.com.petpal.petpal.model.ChatMessage;
import sg.com.petpal.petpal.model.ChatRoom;
import sg.com.petpal.petpal.model.Gender;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.model.Pet;
import sg.com.petpal.petpal.repository.ChatMessageRepository;
import sg.com.petpal.petpal.repository.ChatRoomRepository;
import sg.com.petpal.petpal.repository.PetRepository;

@Component
public class DataLoader {

    private PetRepository petRepository;
    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;

    public DataLoader(PetRepository petRepository,
        ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository) {
            this.petRepository = petRepository;
            this.chatRoomRepository = chatRoomRepository;
            this.chatMessageRepository = chatMessageRepository;
    }

    @PostConstruct
    public void loadData() {

        deleteAllExistingData();
        
        // Create the fist Pet (Buddy)
        Pet pet1 = new Pet();
        pet1.setName("Buddy");
        pet1.setGender(Gender.MALE);
        pet1.setAge(2);
        pet1.setNeutered(true);
        //pet.setOwner(owner);

        // Save Owner and Pet
        //ownerRepository.save(owner);
        petRepository.save(pet1);
        
        
        // Create the second Pet (Happy)
        Pet pet2 = new Pet();
        pet2.setName("Happy");
        pet2.setGender(Gender.FEMALE);
        pet2.setAge(3);
        pet2.setNeutered(false);
        //pet.setOwner(owner);

        // Save Owner and Pet
        //ownerRepository.save(owner);
        petRepository.save(pet2);
        
        // Create the third Pet (Charlie)
        Pet pet3 = new Pet();
        pet3.setName("Charlie");
        pet3.setGender(Gender.MALE);
        pet3.setAge(2);
        pet3.setNeutered(false);
        //pet.setOwner(owner);

        // Save Owner and Pet
        //ownerRepository.save(owner);
        petRepository.save(pet3);

        List<Owner> newOwners = loadOwnersData(3);
        ChatRoom newChatRoom = loadChatRoomData(newOwners);
        List<ChatMessage> newChatMessages = loadChatMessagesData(3, newChatRoom, newOwners.get(0));
        
        chatRoomRepository.save(newChatRoom);
        chatMessageRepository.saveAll(newChatMessages);
        
    }

    private void deleteAllExistingData() {
        // petRepository.deleteAll();
        chatRoomRepository.deleteAll();
        chatMessageRepository.deleteAll();
    }

    // Create 3 owners
    private List<Owner> loadOwnersData(int quantity) {
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Owner newOwner = Owner.builder()
                .name("petpalowner" + i)
                .areaLocation("NTU Street " + i)
                .ownerAuth(null)
                .build();
            owners.add(newOwner);
        }
        return owners;
    }
    
    // Create 1 chat room
    private ChatRoom loadChatRoomData(List<Owner> owners) {
        return ChatRoom.builder()
            .owners(owners)
            .build();
    }

    // Create 3 chat messages for 1 owner, 1 chat room
    private List<ChatMessage> loadChatMessagesData(int quantity, ChatRoom newChatRoom, Owner newOwner) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            ChatMessage newChatMessage = ChatMessage.builder()
                .createdTimestamp(LocalDateTime.now())
                .updatedTimestamp(LocalDateTime.now())
                .message("Chat Message " + i)
                .owner(newOwner)
                .chatRoom(newChatRoom)
                .build();
            chatMessages.add(newChatMessage);
        }
        return chatMessages;
    }

}

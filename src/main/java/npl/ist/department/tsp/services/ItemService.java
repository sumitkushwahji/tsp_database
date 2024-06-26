package npl.ist.department.tsp.services;

import npl.ist.department.tsp.models.Item;
import npl.ist.department.tsp.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Item item) {
        // Validate item before saving
        validateItem(item);
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item newItem) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();
            // Update fields of existing item with values from new item
            existingItem.setItemName(newItem.getItemName());
            existingItem.setEmployeeId(newItem.getEmployeeId());
            existingItem.setIndentor(newItem.getIndentor());
            existingItem.setTotalQty(newItem.getTotalQty());
            existingItem.setTenderNo(newItem.getTenderNo());
            existingItem.setPurchaseOrderDate(newItem.getPurchaseOrderDate());
            existingItem.setPurchaseOrderNo(newItem.getPurchaseOrderNo());
            existingItem.setDateOfDeliveryISTRAC(newItem.getDateOfDeliveryISTRAC());
            existingItem.setQuantityISTRAC(newItem.getQuantityISTRAC());
            existingItem.setInstallationDateISTRAC(newItem.getInstallationDateISTRAC());
            existingItem.setDateOfDeliveryNPL(newItem.getDateOfDeliveryNPL());
            existingItem.setQuantityNPL(newItem.getQuantityNPL());
            existingItem.setInstallationDateNPL(newItem.getInstallationDateNPL());
            existingItem.setWhiteSlipDate(newItem.getWhiteSlipDate());
            existingItem.setWhiteSlipNo(newItem.getWhiteSlipNo());
            existingItem.setWhiteSlipGivenBy(newItem.getWhiteSlipGivenBy());
            existingItem.setRemarks(newItem.getRemarks());
            existingItem.setCategory(newItem.getCategory());
            // Update other fields as needed

            return itemRepository.save(existingItem);
        } else {
            throw new IllegalArgumentException("Item not found with id: " + id);
        }
    }


    public void deleteItem(Long id) {
        // Check if item exists before deleting
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Item not found with id: " + id);
        }
    }

    private void validateItem(Item item) {
        // Perform validation logic here
        // For example, check if required fields are not null
        if (item.getItemName() == null || item.getItemName().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        // Add more validation rules as needed
    }

    private void updateItemFields(Item existingItem, Item newItem) {
        // Update only non-null fields from newItem to existingItem
        if (newItem.getItemName() != null) {
            existingItem.setItemName(newItem.getItemName());
        }
        if (newItem.getEmployeeId() != null) {
            existingItem.setEmployeeId(newItem.getEmployeeId());
        }
        // Update other fields similarly
    }
}

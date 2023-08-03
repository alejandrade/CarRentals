import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Paper,
    Checkbox
} from '@mui/material';
import userService from "../../services/user/UserService";
import {UserWithDetailsDto} from "../../services/user/UserService.types";
import Pagination from '@mui/material/Pagination';
import CustomToolbar from "../../components/CustomToolbar";
import EditUserModal from "./EditUserModal";
import Typography from "@mui/material/Typography";

const UserDetailsTable: React.FC = () => {
    const [users, setUsers] = useState<UserWithDetailsDto[]>([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [selectedUserId, setSelectedUserId] = useState<string | null>(null); // to store the selected user ID
    async function fetchUsers() {
        try {
            const userDetails = await userService.getUserWithDetails(page);
            setUsers(userDetails.content);
            setTotalPages(userDetails.totalPages);
        } catch (error) {
            console.error("Error fetching user details:", error);
        }
    }

    useEffect(() => {
        fetchUsers();
    }, [page]);

    useEffect(() => {
        fetchUsers();
    }, []);

    const handleRowSelection = (userId: string) => {
        if (selectedUserId === userId) {
            setSelectedUserId(null);
        } else
            setSelectedUserId(userId);
    }

    return (<>

        <Paper>
            <Typography variant={"h4"}>Users</Typography>
            {selectedUserId &&
                <CustomToolbar>
                    <EditUserModal onSave={fetchUsers} userId={selectedUserId}/>
                </CustomToolbar>
            }
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Select</TableCell>
                        <TableCell>Email</TableCell>
                        <TableCell>Phone Number</TableCell>
                        <TableCell>First Name</TableCell>
                        <TableCell>Last Name</TableCell>
                        <TableCell>Authorities</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map((user) => (
                        <TableRow key={user.id} onClick={() => handleRowSelection(user.id)}>
                            <TableCell>
                                <Checkbox
                                    checked={selectedUserId === user.id}
                                    onChange={() => handleRowSelection(user.id)}
                                />
                            </TableCell>
                            <TableCell>{user.email}</TableCell>
                            <TableCell>{user.phoneNumber}</TableCell>
                            <TableCell>{user.firstName}</TableCell>
                            <TableCell>{user.lastName}</TableCell>
                            <TableCell>{user.authorities.join(', ')}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            <Pagination count={totalPages} page={page + 1}
                        onChange={(event, value) => setPage(value - 1)} />
        </Paper>
        </>
    );
}

export default UserDetailsTable;
